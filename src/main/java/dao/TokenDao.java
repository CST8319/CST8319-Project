package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import services.TokenService;

/**
 * Data Access Object (DAO) class for handling database operations related to
 * tokens.
 * Implements the TokenService interface to manage verification and reset
 * tokens.
 */
public class TokenDao implements TokenService {

    /**
     * Saves a verification code for a given email in the database. If a record
     * already exists,
     * it updates the token.
     * 
     * @param email            the email address associated with the verification
     *                         code
     * @param verificationCode the verification code to be saved
     */
    public void saveVerificationCode(String email, String verificationCode) {
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlInsert = "INSERT INTO verification_tokens (email, token, type) VALUES (?, ?, 'verification') ON DUPLICATE KEY UPDATE token=?";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, email);
            statement.setString(2, verificationCode);
            statement.setString(3, verificationCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a reset code for a given email in the database. If a record already
     * exists,
     * it updates the token.
     * 
     * @param email     the email address associated with the reset code
     * @param resetCode the reset code to be saved
     */
    public void saveResetCode(String email, String resetCode) {
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlInsert = "INSERT INTO verification_tokens (email, token, type) VALUES (?, ?, 'reset') ON DUPLICATE KEY UPDATE token=?";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, email);
            statement.setString(2, resetCode);
            statement.setString(3, resetCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the verification code associated with a given email.
     * 
     * @param email the email address for which the verification code is to be
     *              retrieved
     * @return the verification code if found, otherwise null
     */
    public String getVerificationCode(String email) {
        String token = null;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlQuery = "SELECT token FROM verification_tokens WHERE email = ? AND type = 'verification'";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                token = resultSet.getString("token");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * Retrieves the reset code associated with a given email.
     * 
     * @param email the email address for which the reset code is to be retrieved
     * @return the reset code if found, otherwise null
     */
    public String getResetCode(String email) {
        String token = null;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlQuery = "SELECT token FROM verification_tokens WHERE email = ? AND type = 'reset'";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                token = resultSet.getString("token");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * Validates a verification code for a given email.
     * 
     * @param email            the email address to validate the verification code
     *                         for
     * @param verificationCode the verification code to validate
     * @return true if the verification code is valid, otherwise false
     */
    public boolean validateVerificationCode(String email, String verificationCode) {
        boolean isValid = false;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlCheckToken = "SELECT * FROM verification_tokens WHERE email = ? AND token = ? AND type = 'verification'";
            PreparedStatement statement = connection.prepareStatement(sqlCheckToken);
            statement.setString(1, email);
            statement.setString(2, verificationCode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Validates a reset code for a given email and deletes the token upon
     * successful validation.
     * 
     * @param email     the email address to validate the reset code for
     * @param resetCode the reset code to validate
     * @return true if the reset code is valid, otherwise false
     */
    public boolean validateResetCode(String email, String resetCode) {
        boolean isValid = false;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlCheckToken = "SELECT * FROM verification_tokens WHERE email = ? AND token = ? AND type = 'reset'";
            PreparedStatement statement = connection.prepareStatement(sqlCheckToken);
            statement.setString(1, email);
            statement.setString(2, resetCode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
                deleteVerificationToken(email, resetCode, "reset");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Deletes a token of a specific type for a given email.
     * 
     * @param email the email address associated with the token to be deleted
     * @param token the token to be deleted
     * @param type  the type of the token (e.g., 'verification' or 'reset')
     */
    public void deleteVerificationToken(String email, String token, String type) {
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlDelete = "DELETE FROM verification_tokens WHERE email = ? AND token = ? AND type = ?";
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setString(1, email);
            statement.setString(2, token);
            statement.setString(3, type);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
