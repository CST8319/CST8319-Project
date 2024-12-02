package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import services.TokenService;

public class TokenDao implements TokenService {

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
