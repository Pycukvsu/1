package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    private static String dbUsername = "postgres";
    private static String dbPassword = "12345";


    public static Connection getConnection(){           // создание соединения через driverManager
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
