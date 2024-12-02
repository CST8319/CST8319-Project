 package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.Profile;
import beans.ProfileBuilder;
import services.ProfileService;

public class ProfileDao implements ProfileService {

    public Profile getProfileByUserId(int userId) {
        Profile profile = null;
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            String sqlQuery = "SELECT * FROM profile WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                profile = new ProfileBuilder(resultSet.getInt("userId"))
                    .setFirstName(resultSet.getString("firstName"))
                    .setLastName(resultSet.getString("lastName"))
                    .setAge(resultSet.getInt("age"))
                    .setGender(resultSet.getString("gender"))
                    .setWeight(resultSet.getInt("weight"))
                    .setHeight(resultSet.getInt("height"))
                    .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    // Split this large function into several smaller ones
    public void saveOrUpdateProfile(Profile profile) {
        try {
            Connection connection = DBConnection.getConnectionToDatabase();
            if (profileCheck(profile.getUserId(), connection)) {
            	updateProfile(profile, connection);
            } else {
            	saveProfile(profile, connection);
            } 
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
       
    private boolean profileCheck(int userId, Connection connection) throws SQLException {
        String sqlQuery = "SELECT * FROM profile WHERE userId = ?";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    
    private void updateProfile(Profile profile, Connection connection) throws SQLException {
        String sqlUpdate = "UPDATE profile SET firstName = ?, lastName = ?, age = ?, gender = ?, weight = ?, height = ? WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {
        	statement.setString(1, profile.getFirstName());
        	statement.setString(2, profile.getLastName());
        	statement.setInt(3, profile.getAge());
        	statement.setString(4, profile.getGender());
        	statement.setInt(5, profile.getWeight());
        	statement.setInt(6, profile.getHeight());
        	statement.setInt(7, profile.getUserId());
        	statement.executeUpdate();
        }
    }
            
    private void saveProfile(Profile profile, Connection connection) throws SQLException {
        String sqlInsert = "INSERT INTO profile (userId, firstName, lastName, age, gender, weight, height) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlInsert)) {       
                statement.setInt(1, profile.getUserId());
                statement.setString(2, profile.getFirstName());
                statement.setString(3, profile.getLastName());
                statement.setInt(4, profile.getAge());
                statement.setString(5, profile.getGender());
                statement.setInt(6, profile.getWeight());
                statement.setInt(7, profile.getHeight());
                statement.executeUpdate();
            }
        }
    }
