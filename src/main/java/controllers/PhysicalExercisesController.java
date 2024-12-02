package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PhysicalExercisesController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Forward to the physical exercises page
        request.getRequestDispatcher("/WEB-INF/views/physicalExercises.jsp").forward(request, response);
	}

}
