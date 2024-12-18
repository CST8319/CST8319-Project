package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DBConnection} class provides a utility method to establish a
 * connection
 * to the MySQL database used in the application.
 */
public class DBConnection {

    // Database user credentials
    private static final String dbUser = "root";
    private static final String dbPassword = "admin";

    // Connection string for the MySQL database
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/getwell_getfit_db";

    /**
     * Establishes and returns a connection to the database.
     * 
     * @return a {@link Connection} object to interact with the database, or
     *         {@code null} if a connection could not be established.
     */
    public static Connection getConnectionToDatabase() {
        Connection connection = null;
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection to the database
            connection = DriverManager.getConnection(CONN_STRING, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            // Handle missing MySQL JDBC Driver
            System.out.println("Missing MySQL JDBC Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }
        return connection;
    }
}
