package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MentalHealthActivitiesController implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the mental health activities page
        request.getRequestDispatcher("/WEB-INF/views/mentalHealthActivities.jsp").forward(request, response);
    }
}
