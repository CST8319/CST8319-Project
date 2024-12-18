package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import factories.ExerciseFactory;
import models.Exercise;
/*
 * Database access object (DAO) for requests related to favorites, including adding, removing and retrieving a user's favorites 
 * from the favorites and exercises table.
 */
public class FavoritesDao {
	
	private static final String ADD_FAVORITE_BY_EXERCISE_ID = "INSERT INTO favorites (user_id, exercise_id, date_favorited) VALUES (?, ?, ?);";
	private static final String UNFAVORITE_BY_EXERCISE_ID = "DELETE FROM favorites WHERE user_id = ? AND exercise_id = ?;";
	private static final String DISPLAY_FAVORITES = "SELECT e.id, e.name, e.category, e.description FROM exercises as e "
			+ "JOIN favorites as f ON f.exercise_id = e.id WHERE user_id = ?;";		
			
			
    protected Connection getConnection() {
        return DBConnection.getConnectionToDatabase();
    }
    
    public HashMap<Integer, Exercise> displayFavorites (int userId) {
    	  
    	HashMap<Integer, Exercise> favorites = new HashMap<Integer, Exercise>();
    	try {
    		Connection connection = getConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(DISPLAY_FAVORITES);
    		preparedStatement.setInt(1, userId);
    		ResultSet rs = preparedStatement.executeQuery();
    		while (rs.next()) {
    			//exercise id, exercise name, category, description
    			int exerciseId = rs.getInt("id");
    			String name = rs.getString("name");
    			String category = rs.getString("category");
    			String description = rs.getString("description");
    			Exercise exercise = ExerciseFactory.createExercise(exerciseId, name, category, description);
    			favorites.put(exerciseId, exercise);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	if (favorites.equals(null) ) {
			favorites = new HashMap<Integer, Exercise>(); //if there are no favorites set to empty instead of null
		}
    	return favorites;
    }

    public void addFavorite (int userId, int exerciseId) {
     
    	List<Exercise> exercises = new ArrayList<>();
        try {
        	Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_FAVORITE_BY_EXERCISE_ID); 
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, exerciseId);
            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public void unFavorite (int userId, int exerciseId) {
	    
		List<Exercise> exercises = new ArrayList<>();
	    try {
	    	Connection connection = getConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(UNFAVORITE_BY_EXERCISE_ID);
	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, exerciseId);
	        preparedStatement.executeUpdate();
	      
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
   }

}
