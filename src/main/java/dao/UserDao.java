package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.Register;
import factories.RegistrationFactory;
import services.UserService;

public class UserDao implements UserService<Register>{

    public void newUser(Register register){
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
    
    public Register getUser(String usernameOrEmail){
    	Register user = null;
    	try {
    		Connection connection = DBConnection.getConnectionToDatabase();
    		String sqlQuery = "SELECT * FROM registration WHERE username = ? OR email =?";
    		PreparedStatement stmt = connection.prepareStatement(sqlQuery);
    		stmt.setString(1, usernameOrEmail);
    		stmt.setString(2, usernameOrEmail);
    		ResultSet result = stmt.executeQuery();
    		if (result.next());
    		user = RegistrationFactory.createUserFromDatabase(
    				result.getInt("id"),
    				result.getString("email"),
    				result.getString("username"),
    				result.getString("password"),
    				result.getBoolean("isVerified"));
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		return user;
    }

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
