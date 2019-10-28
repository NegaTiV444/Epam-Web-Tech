package epam.webtech.model.user;

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

public class UserMigrationService implements XmlMigrationService<User> {

    private static final String XSD_FILE_NAME = "users.xsd";
    private static final String TABLE = "users";

    private XsdValidationService validationService = XsdValidationService.getInstance();
    private JdbcService jdbcService = JdbcService.getInstance();

    private XmlMapper xmlMapper = new XmlMapper();

    private UserMigrationService() {
    }

    private static class SingletonHandler {
        static final UserMigrationService INSTANCE = new UserMigrationService();
    }

    public static UserMigrationService getInstance() {
        return UserMigrationService.SingletonHandler.INSTANCE;
    }

    @Override
    public List<User> validateAndMigrate(File xmlDataFile) throws ValidationException {
        File xsdFile = new File(XSD_FILE_NAME);
        validationService.validate(xmlDataFile, xsdFile);
        System.out.println("Validation successful");
        List<User> users;
        try {
            String xml = inputStreamToString(new FileInputStream(xmlDataFile));
            users = xmlMapper.readValue(xml, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            //TODO log
            throw new ValidationException("Error " + e.getMessage());
        }
        users.forEach(user -> {
            try {
                saveUser(user);
            } catch (AlreadyExistsException e) {
                System.out.println(e.getMessage());
                //TODO log
            }
        });
        return users;
    }

    private void saveUser(User user) throws AlreadyExistsException {
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM " + TABLE + " WHERE name = '" + user.getName() + "' ;";
            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                //TODO log
                throw new AlreadyExistsException("Record User with name " + user.getName() + " already exists id database");
            }
            query = "INSERT INTO " + TABLE + " (id, name, passwordHash, bank, authorityLvl ) VALUES ( '"
                    + user.getId() + "', '" + user.getName() + "', '" + user.getPasswordHash() + "', '"
                    + user.getBank() + "', '" + user.getAuthorityLvl() + "' );";
            preparedStatement.executeUpdate(query);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            //TODO log
            System.exit(1);
        }
    }

}
