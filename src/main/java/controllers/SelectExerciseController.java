package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ExerciseDao;
import models.Exercise;

public class SelectExerciseController implements Controller{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
            handleGetRequest(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
	}
	
	 private void handleGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String exerciseId = request.getParameter("exerciseId");
	        if (exerciseId != null && !exerciseId.isEmpty()) {
	            ExerciseDao exerciseDao = new ExerciseDao();
	            Exercise exercise = exerciseDao.getExerciseById(Integer.parseInt(exerciseId));
	            request.setAttribute("exercise", exercise);
	            request.getRequestDispatcher("/WEB-INF/views/exercise.jsp").forward(request, response);
	        } else {
	            response.sendRedirect("selectCategory");
	        }
	    }
	}


