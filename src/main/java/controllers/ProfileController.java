package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Profile;
import beans.ProfileBuilder;
import dao.ProfileDao;

/**
 * Controller for handling user profile viewing and updating.
 * It processes both GET and POST requests for viewing and updating the user
 * profile.
 * 
 * On GET request, it retrieves and displays the user's profile information.
 * On POST request, it processes and saves the updated profile information.
 */
public class ProfileController implements Controller {

    /**
     * Executes the logic for handling user profile requests.
     * <p>
     * On GET request, it displays the user's profile.
     * On POST request, it processes the updated profile data and saves it to the
     * database.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        }
    }

    /**
     * Handles the GET request for displaying the user's profile.
     * It retrieves the user's profile from the database and sets the profile
     * attributes for the view.
     * If the user is not logged in, it redirects to the login page.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login");
        } else {
            ProfileDao dao = new ProfileDao();
            Profile profile = dao.getProfileByUserId(userId);
            if (profile != null) {
                request.setAttribute("firstName", profile.getFirstName());
                request.setAttribute("lastName", profile.getLastName());
                request.setAttribute("age", profile.getAge());
                request.setAttribute("gender", profile.getGender());
                request.setAttribute("weight", profile.getWeight());
                request.setAttribute("height", profile.getHeight());
            }
            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
        }
    }

    /**
     * Handles the POST request for updating the user's profile.
     * It retrieves the updated profile data from the request, constructs a new
     * Profile object,
     * and saves it to the database. A success message is set as an attribute for
     * the view.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login");
        } else {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int age = parseOrDefault(request.getParameter("age"), 0);
            String gender = request.getParameter("gender");
            int weight = parseOrDefault(request.getParameter("weight"), 0);
            int height = parseOrDefault(request.getParameter("height"), 0);

            // Construct a Profile object using the ProfileBuilder
            Profile profile = new ProfileBuilder(userId)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age)
                    .setGender(gender)
                    .setWeight(weight)
                    .setHeight(height)
                    .build();

            ProfileDao dao = new ProfileDao();
            dao.saveOrUpdateProfile(profile);

            request.setAttribute("message", "Profile updated successfully.");
            doGet(request, response); // Reload the profile page to reflect the changes
        }
    }

    /**
     * Tries to parse the given string as an integer. If the parsing fails, it
     * returns a default value.
     * 
     * @param value        the string value to parse
     * @param defaultValue the default value to return if parsing fails
     * @return the parsed integer or the default value if parsing fails
     */
    private int parseOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
