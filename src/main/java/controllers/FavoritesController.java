package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ExerciseDao;
import dao.FavoritesDao;
import factories.ExerciseFactory;
import models.Exercise;

public class FavoritesController implements Controller  {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
	
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			//Favoriting and unfavorting from Category.jsp
			if (action.equalsIgnoreCase("fav_category") || action.equalsIgnoreCase("unfav_category") ) {
				favoriteFromCategories(action, request, response);
			}
			//Clicking Favorites button in header or unFavoriting from favorites page
			if (action.equalsIgnoreCase("view_favorites")) {
				viewFavorites(request, response);
			}
			if (action.equalsIgnoreCase("unfav_favorites")) {
				unfavoriteFromFavorites(request, response);
			}
	
		}
	}
	public void favoriteFromCategories (String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		Integer userId = (Integer) session.getAttribute("userId"); 
		String category = request.getParameter("category");
		int exerciseId = Integer.parseInt(request.getParameter("id"));
		
		//favorites stored as session variable on log in (should be not be null)
		HashMap<Integer, Exercise> favorites = (HashMap<Integer, Exercise>) session.getAttribute("favorites");
		//If favorites is null initalize new favorites hashmap 
		if (favorites == null) {
			favorites = new HashMap<Integer, Exercise>();
		}
		
		FavoritesDao favoritesDao = new FavoritesDao();
		
		if (action.equalsIgnoreCase("fav_category")) {
			//favoritesDao.addFavorite(userId, exerciseId);
			favorites.put(exerciseId, ExerciseFactory.createExercise(exerciseId, "name", category, "description"));
		} else {
			//favoritesDao.unFavorite(userId, exerciseId);
			favorites.remove(exerciseId);
		}
		
		ExerciseDao exerciseDao = new ExerciseDao();
		List<Exercise> exercises = exerciseDao.getExercisesByCategory(category);
		//after adding or removing retrieve current favorite list from Dao
		//favorites = favoritesDao.displayFavorites(userId);
		session.setAttribute("favorites", favorites);
		request.setAttribute("category", category);
        request.setAttribute("exercises", exercises);
		request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
		
	}
	
	public void viewFavorites (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		Integer userId = (Integer) session.getAttribute("userId"); 
		HashMap<Integer, Exercise> favorites = (HashMap<Integer, Exercise>) session.getAttribute("favorites"); //should not be null when set at login (maybe empty)
		if (favorites == null) {
			favorites = new HashMap<Integer, Exercise>();
		}
		
		session.setAttribute("favorites", favorites); //might be optional
		request.getRequestDispatcher("/WEB-INF/views/favorites.jsp").forward(request, response);
		
	}
	
	public void unfavoriteFromFavorites (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		Integer userId = (Integer) session.getAttribute("userId"); 
		int exerciseId = Integer.parseInt(request.getParameter("id"));
		
		HashMap<Integer, Exercise> favorites = (HashMap<Integer, Exercise>) session.getAttribute("favorites"); //should not be null when set at login (maybe empty)
		favorites.remove(exerciseId);
		
		//FavoritesDao favoritesDao = new FavoritesDao();
		//favoritesDao.unFavorite(userId, exerciseId);
		
		//HashMap<Integer, Exercise> favorites = favoritesDao.displayFavorites(userId);
		
		session.setAttribute("favorites", favorites); //might be optional
		request.getRequestDispatcher("/WEB-INF/views/favorites.jsp").forward(request, response);
		
	}
}
