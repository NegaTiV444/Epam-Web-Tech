package epam.webtech.model.bet;

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

public class BetMigrationService implements XmlMigrationService<Bet> {

    private static final String XSD_FILE_NAME = "bets.xsd";
    private static final String TABLE = "bets";

    private XsdValidationService validationService = XsdValidationService.getInstance();
    private JdbcService jdbcService = JdbcService.getInstance();

    private XmlMapper xmlMapper = new XmlMapper();

    private BetMigrationService() {
    }

    private static class SingletonHandler {
        static final BetMigrationService INSTANCE = new BetMigrationService();
    }

    public static BetMigrationService getInstance() {
        return BetMigrationService.SingletonHandler.INSTANCE;
    }

    @Override
    public List<Bet> validateAndMigrate(File xmlDataFile) throws ValidationException {
        File xsdFile = new File(XSD_FILE_NAME);
        validationService.validate(xmlDataFile, xsdFile);
        System.out.println("Validation successful");
        List<Bet> bets;
        try {
            String xml = inputStreamToString(new FileInputStream(xmlDataFile));
            bets = xmlMapper.readValue(xml, new TypeReference<List<Bet>>() {
            });
        } catch (IOException e) {
            //TODO log
            throw new ValidationException("Error " + e.getMessage());
        }
        bets.forEach(bet -> {
            try {
                saveBet(bet);
            } catch (AlreadyExistsException e) {
                System.out.println(e.getMessage());
                //TODO log
            }
        });
        return bets;
    }

    private void saveBet(Bet bet) throws AlreadyExistsException {
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM " + TABLE + " WHERE id = '" + bet.getId() + "' ;";
            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                //TODO log
                throw new AlreadyExistsException("Record Bet with id " + bet.getId() + " already exists id database");
            }
            query = "INSERT INTO " + TABLE + " (id, amount, race_id, horse_name, user_name ) VALUES ( '"
                    + bet.getId() + "', '" + bet.getAmount() + "', '" + bet.getRaceId() + "', '"
                    + bet.getHorseName() + "', '" + bet.getUserName() + "' );";
            preparedStatement.executeUpdate(query);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            //TODO log
            System.exit(1);
        }
    }
}
