package epam.webtech.model.race;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.ValidationException;
import epam.webtech.model.XmlMigrationService;
import epam.webtech.services.JdbcService;
import epam.webtech.services.XsdValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceMigrationService implements XmlMigrationService<Race> {

    private static final String XSD_FILE_NAME = "races.xsd";
    private static final String TABLE = "races";

    private final Logger logger = LogManager.getLogger(RaceMigrationService.class);

    private XsdValidationService validationService = XsdValidationService.getInstance();
    private JdbcService jdbcService = JdbcService.getInstance();

    private XmlMapper xmlMapper = new XmlMapper();

    private RaceMigrationService() {
    }

    private static class SingletonHandler {
        static final RaceMigrationService INSTANCE = new RaceMigrationService();
    }

    public static RaceMigrationService getInstance() {
        return RaceMigrationService.SingletonHandler.INSTANCE;
    }

    @Override
    public List<Race> validateAndMigrate(File xmlDataFile) throws ValidationException {
        File xsdFile = new File(XSD_FILE_NAME);
        validationService.validate(xmlDataFile, xsdFile);
        System.out.println("Validation successful");
        List<Race> races;
        try {
            String xml = inputStreamToString(new FileInputStream(xmlDataFile));
            races = xmlMapper.readValue(xml, new TypeReference<List<Race>>() {
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ValidationException("Error " + e.getMessage());
        }
        AtomicInteger counter = new AtomicInteger();
        races.forEach(race -> {
            try {
                saveRace(race);
                counter.getAndIncrement();
            } catch (AlreadyExistsException e) {
                logger.debug(e);
                System.out.println(e.getMessage());
            }
        });
        logger.debug("Total races: " + races.size() + ", successful migrated: " + counter);
        System.out.println("Migration successful");
        return races;
    }

    private void saveRace(Race race) throws AlreadyExistsException {
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM " + TABLE + " WHERE id = '" + race.getId() + "' ;";
            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                throw new AlreadyExistsException("Record Race with id " + race.getId() + " already exists id database");
            }
            StringBuilder horsesString = new StringBuilder();
            for (String name : race.getHorsesNames())
                horsesString.append(name + ",");
            if (horsesString.length() > 1)
                horsesString.deleteCharAt(horsesString.length() - 1);
            query = "INSERT INTO " + TABLE + " (id, race_date, status, horses_names, winner ) VALUES ( '"
                    + race.getId() + "', '" + race.getDate().getTime() + "', '" + race.getStatus().getPriority() + "', '"
                    + horsesString.toString() + "', '" + race.getWinnerHorseName() + "' );";
            preparedStatement.executeUpdate(query);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.fatal(e);
            System.exit(1);
        }
    }
}
