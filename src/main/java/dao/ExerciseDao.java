package dao;

import models.Exercise;
import factories.ExerciseFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDao {

    private static final String SELECT_EXERCISES_BY_CATEGORY = "SELECT * FROM exercises WHERE category = ?";
    private static final String SEARCH_EXERCISES_BY_NAME = "SELECT * FROM exercises WHERE name LIKE ?";
    private static final String SELECT_EXERCISE_BY_ID = "SELECT * FROM exercises WHERE id = ?";

    protected Connection getConnection() {
        return DBConnection.getConnectionToDatabase();
    }

    public List<Exercise> getExercisesByCategory(String category) {
        List<Exercise> exercises = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXERCISES_BY_CATEGORY)) {
            preparedStatement.setString(1, category);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String imageUrl = rs.getString("image_url");
                String instructions = rs.getString("instructions");

                Exercise exercise = ExerciseFactory.createExercise(id, name, category, description, imageUrl, instructions);
                exercises.add(exercise);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public List<Exercise> searchExercises(String query) {
        List<Exercise> exercises = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_EXERCISES_BY_NAME)) {
            String searchQuery = "%" + query + "%";
            preparedStatement.setString(1, searchQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                String imageUrl = rs.getString("image_url");
                String instructions = rs.getString("instructions");

                Exercise exercise = ExerciseFactory.createExercise(id, name, category, description, imageUrl, instructions);
                exercises.add(exercise);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public Exercise getExerciseById(int id) {
        Exercise exercise = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXERCISE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                String imageUrl = rs.getString("image_url");
                String instructions = rs.getString("instructions");

                exercise = ExerciseFactory.createExercise(id, name, category, description, imageUrl, instructions);

          }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercise;
    }

}

