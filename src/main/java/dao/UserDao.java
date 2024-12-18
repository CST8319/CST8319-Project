package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Register;
import factories.RegistrationFactory;
import services.UserService;

/**
 * Data Access Object (DAO) class for handling user-related operations in the
 * database.
 * Implements the {@link UserService} interface to provide CRUD functionality
 * for user data.
 */
public class UserDao implements UserService<Register> {

    /**
     * Registers a new user by inserting their details into the database.
     *
     * @param register the {@link Register} object containing the user's details
     */
    public void newUser(Register register) {
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlInsert = "INSERT INTO registration (username, password, email, isVerified) VALUES (?, ?, ?, false)";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, register.getUsername());
            statement.setString(2, register.getPassword());
            statement.setString(3, register.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether a username exists in the database.
     *
     * @param username the username to check
     * @return {@code true} if the username exists, {@code false} otherwise
     */
    public boolean userCheck(String username) {
        boolean exists = false;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlCheckUser = "SELECT * FROM registration WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCheckUser);
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Checks whether an email exists in the database.
     *
     * @param email the email to check
     * @return {@code true} if the email exists, {@code false} otherwise
     */
    public boolean emailCheck(String email) {
        boolean exists = false;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlCheckUser = "SELECT * FROM registration WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCheckUser);
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Validates a user's credentials by checking the username/email and password
     * combination.
     *
     * @param usernameOrEmail the username or email of the user
     * @param password        the password of the user
     * @return {@code true} if the credentials are valid, {@code false} otherwise
     */
    public boolean validateUser(String usernameOrEmail, String password) {
        boolean isValid = false;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlValidate = "SELECT * FROM registration WHERE (username = ? OR email = ?) AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sqlValidate);
            statement.setString(1, usernameOrEmail);
            statement.setString(2, usernameOrEmail);
            statement.setString(3, password);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Checks whether a user is verified.
     *
     * @param usernameOrEmail the username or email of the user
     * @return {@code true} if the user is verified, {@code false} otherwise
     */
    public boolean isUserVerified(String usernameOrEmail) {
        boolean isVerified = false;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlCheckVerified = "SELECT isVerified FROM registration WHERE username = ? OR email = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCheckVerified);
            statement.setString(1, usernameOrEmail);
            statement.setString(2, usernameOrEmail);
            ResultSet set = statement.executeQuery();
            if (set.next() && set.getBoolean("isVerified")) {
                isVerified = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isVerified;
    }

    /**
     * Checks whether a user with the given ID is verified.
     *
     * @param userId the ID of the user
     * @return {@code true} if the user is verified, {@code false} otherwise
     */
    public boolean isUserVerified(int userId) {
        boolean isVerified = false;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlCheckVerified = "SELECT isVerified FROM registration WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlCheckVerified);
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            if (set.next() && set.getBoolean("isVerified")) {
                isVerified = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isVerified;
    }

    /**
     * Marks a user as verified in the database.
     *
     * @param email the email of the user to verify
     */
    public void markUserAsVerified(String email) {
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlUpdate = "UPDATE registration SET isVerified = true WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a user's details based on their username or email.
     *
     * @param usernameOrEmail the username or email of the user
     * @return a {@link Register} object containing the user's details, or
     *         {@code null} if not found
     */
    public Register getUser(String usernameOrEmail) {
        Register user = null;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlQuery = "SELECT * FROM registration WHERE username = ? OR email = ?";
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                user = RegistrationFactory.createUserFromDatabase(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getBoolean("isVerified"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Updates a user's password in the database.
     *
     * @param email       the email of the user
     * @param newPassword the new password to set
     */
    public void updatePassword(String email, String newPassword) {
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlUpdate = "UPDATE registration SET password = ? WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, newPassword);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
