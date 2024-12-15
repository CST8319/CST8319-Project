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
		int exerciseId = Integer.parseInt(request.getParameter("id"));
		String category = request.getParameter("category");
		
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			
			//
			HttpSession session = request.getSession();
			Integer userId = (Integer) session.getAttribute("userId");
			HashMap<Integer, Exercise> favorites = (HashMap<Integer, Exercise>) session.getAttribute("favorites");
			//If favorites is null initalized new favorites hashmap 
			if (favorites == null) {
				favorites = new HashMap<Integer, Exercise>();
			}
			
			FavoritesDao favoritesDao = new FavoritesDao();
			
			if (action.equalsIgnoreCase("favorite")) {
				//favoritesDao.addFavorite(userId, exerciseId);
				favorites.put(exerciseId, ExerciseFactory.createExercise(exerciseId, "name", category, "description"));
			} else {
				//favoritesDao.unFavorite(userId, exerciseId);
				favorites.remove(exerciseId);
			}
			
			ExerciseDao exerciseDao = new ExerciseDao();
			List<Exercise> exercises = exerciseDao.getExercisesByCategory(category);
			//HashMap<Integer, Exercise> favorites = favoritesDao.displayFavorites(userId);
			
			session.setAttribute("favorites", favorites);
			request.setAttribute("category", category);
	        request.setAttribute("exercises", exercises);
			request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
		}
	}
	
}
