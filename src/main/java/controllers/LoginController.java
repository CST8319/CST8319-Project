package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Register;
import dao.FavoritesDao;
import dao.UserDao;
import models.Exercise;

/**
 * Controller for handling user login functionality.
 * It processes both GET and POST requests for logging in.
 * If the login is successful, the user is redirected to the homepage,
 * otherwise, an error message is displayed.
 */
public class LoginController implements Controller {

    /**
     * Executes the logic for handling user login.
     * <p>
     * On GET request, it displays the login page.
     * On POST request, it validates the user's credentials, stores user information
     * in the session,
     * and redirects the user based on their verification status.
     * <p>
     * If the user is not verified, they are redirected to the verification page.
     * If the login is invalid, an error message is shown and the user is asked to
     * try again.
     *
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handling GET request to display the login page
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
        // Handling POST request to process the login credentials
        else if ("POST".equalsIgnoreCase(request.getMethod())) {
            String usernameOrEmail = request.getParameter("username_or_email");
            String password = request.getParameter("password");
            UserDao userDao = new UserDao();

            // Validate user credentials
            if (userDao.validateUser(usernameOrEmail, password)) {
                // Retrieve user details from the database
                Register user = userDao.getUser(usernameOrEmail);

                // Create a session for the logged-in user
                HttpSession session = request.getSession();
                session.setAttribute("username", user.getUsername()); // Store username in session
                session.setAttribute("userId", user.getId()); // Store userId in session
                session.setAttribute("email", user.getEmail());

                // Retrieve and store the user's favorite exercises in the session
                FavoritesDao favoriteDao = new FavoritesDao();
                HashMap<Integer, Exercise> favorites = favoriteDao.displayFavorites(user.getId()); // DAO ensures not
                                                                                                   // null
                session.setAttribute("favorites", favorites);

                // Check if the user is verified, if not redirect to verification page
                if (!userDao.isUserVerified(usernameOrEmail)) {
                    response.sendRedirect("verify");
                } else {
                    // Forward to the homepage if user is verified
                    request.getRequestDispatcher("/WEB-INF/views/homepage.jsp").forward(request, response);
                }
            } else {
                // If login credentials are invalid, display error message
                request.setAttribute("errorMessage", "Invalid username/email or password.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        }
    }
}
