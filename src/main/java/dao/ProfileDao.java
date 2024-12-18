package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.Profile;
import beans.ProfileBuilder;
import services.ProfileService;

/**
 * Data Access Object (DAO) class for handling database operations related to
 * the Profile entity.
 * Implements the ProfileService interface.
 */
public class ProfileDao implements ProfileService {

    /**
     * Retrieves a Profile object from the database based on the provided userId.
     * 
     * @param userId the unique identifier of the user whose profile is to be
     *               retrieved
     * @return a Profile object if found, otherwise null
     */
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

    /**
     * Saves or updates a Profile in the database. If the profile exists, it updates
     * the existing
     * record; otherwise, it inserts a new record.
     * 
     * @param profile the Profile object to be saved or updated
     */
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

    /**
     * Checks if a profile exists in the database for the given userId.
     * 
     * @param userId     the unique identifier of the user
     * @param connection the database connection
     * @return true if the profile exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    private boolean profileCheck(int userId, Connection connection) throws SQLException {
        String sqlQuery = "SELECT * FROM profile WHERE userId = ?";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    /**
     * Updates an existing Profile in the database.
     * 
     * @param profile    the Profile object containing updated information
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Saves a new Profile to the database.
     * 
     * @param profile    the Profile object to be saved
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */
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
