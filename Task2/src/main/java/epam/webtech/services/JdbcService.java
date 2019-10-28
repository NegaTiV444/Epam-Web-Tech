package epam.webtech.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcService {

    private static final String URL = "jdbc:mysql://localhost:3306/epam?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123321";

    private Connection connection;

    private JdbcService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.exit(652);
        }
    }

    private static class SingletonHandler {
        static final JdbcService INSTANCE = new JdbcService();
    }

    public static JdbcService getInstance() {
        return JdbcService.SingletonHandler.INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }


}
