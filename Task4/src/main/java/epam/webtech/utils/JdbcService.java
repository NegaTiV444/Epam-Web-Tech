package epam.webtech.utils;

import epam.webtech.exceptions.DatabaseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcService {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    private static boolean isInitiated;

    public void init() throws DatabaseException {
        try (InputStream input = new FileInputStream("src/main/resources/conf.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            isInitiated = true;
        } catch (ClassNotFoundException e) {
            isInitiated = false;
            throw new DatabaseException("Failed to load MySQL JDBC Driver");
        } catch (IOException e) {
            isInitiated = false;
            throw new DatabaseException("Failed to load configuration file");
        }
    }

    private JdbcService() {
    }

    public static JdbcService getInstance() throws DatabaseException {
        return JdbcService.SingletonHandler.INSTANCE;
    }

    public Connection getConnection() throws DatabaseException {
        if (!isInitiated) {
            JdbcService.SingletonHandler.INSTANCE.init();
        }
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to establish database connection.");
        }
    }

    private static class SingletonHandler {
        static final JdbcService INSTANCE = new JdbcService();
    }
}
