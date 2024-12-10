package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String dbUser = "root";
    private static final String dbPassword = "admin";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/getwell_getfit_db";

    public static Connection getConnectionToDatabase() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONN_STRING, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            System.out.println("Missing MySQL JDBC Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
