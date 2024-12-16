package controllers;


import models.Exercise;
import dao.ExerciseDao;
import dao.FavoritesDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SelectCategoryController implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            handleGetRequest(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private void handleGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String category = request.getParameter("category");
    
        if (category != null && !category.isEmpty()) {
        	ExerciseDao exerciseDao = new ExerciseDao();
            List<Exercise> exercises = exerciseDao.getExercisesByCategory(category);
            request.setAttribute("category", category);
            request.setAttribute("exercises", exercises);
    
            request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
        } else {
            response.sendRedirect("physicalExercises");
        }
    }
}
