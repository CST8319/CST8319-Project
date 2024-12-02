package controllers;

import dao.ExerciseDao;
import models.Exercise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SearchExerciseController implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String query = request.getParameter("query");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (query != null && !query.trim().isEmpty()) {
            ExerciseDao exerciseDao = new ExerciseDao();
            List<Exercise> exercises = exerciseDao.searchExercises(query);
            if (exercises != null && !exercises.isEmpty()) {
                for (Exercise exercise : exercises) {
                    out.println("<div>");
                    out.println("<a href='selectExercise?exerciseId=" + exercise.getId() + "'>" + exercise.getName() + "</a>");
                    out.println("</div>");
                }
            } else {
                out.println("<p>No exercises found matching your search criteria.</p>");
            }
        }
    }
}
