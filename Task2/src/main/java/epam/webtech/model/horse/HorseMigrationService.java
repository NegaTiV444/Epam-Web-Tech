package epam.webtech.model.horse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.ValidationException;
import epam.webtech.model.XmlMigrationService;
import epam.webtech.services.JdbcService;
import epam.webtech.services.XsdValidationService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HorseMigrationService implements XmlMigrationService<Horse> {

    private static final String XSD_FILE_NAME = "horses.xsd";
    private static final String TABLE = "horses";

    private XsdValidationService validationService = XsdValidationService.getInstance();
    private JdbcService jdbcService = JdbcService.getInstance();

    private XmlMapper xmlMapper = new XmlMapper();

    private HorseMigrationService() {
    }

    private static class SingletonHandler {
        static final HorseMigrationService INSTANCE = new HorseMigrationService();
    }

    public static HorseMigrationService getInstance() {
        return HorseMigrationService.SingletonHandler.INSTANCE;
    }

    @Override
    public List<Horse> validateAndMigrate(File xmlDataFile) throws ValidationException {
        File xsdFile = new File(XSD_FILE_NAME);
        validationService.validate(xmlDataFile, xsdFile);
        System.out.println("Validation successful");
        List<Horse> horses;
        try {
            String xml = inputStreamToString(new FileInputStream(xmlDataFile));
            horses = xmlMapper.readValue(xml, new TypeReference<List<Horse>>() {
            });
        } catch (IOException e) {
            //TODO log
            throw new ValidationException("Error " + e.getMessage());
        }
        horses.forEach(horse -> {
            try {
                saveUser(horse);
            } catch (AlreadyExistsException e) {
                System.out.println(e.getMessage());
                //TODO log
            }
        });
        System.out.println("Migration successful");
        return horses;
    }

    private void saveUser(Horse horse) throws AlreadyExistsException {
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM " + TABLE + " WHERE name = '" + horse.getName() + "' ;";
            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                //TODO log
                throw new AlreadyExistsException("Record Horse with name " + horse.getName() + " already exists id database");
            }
            query = "INSERT INTO " + TABLE + " (id, name, winsCounter) VALUES ( '"
                    + horse.getId() + "', '" + horse.getName() + "', '" + horse.getWinsCounter() + "' );";
            preparedStatement.executeUpdate(query);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            //TODO log
            System.exit(1);
        }
    }
}
