package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;

/**
 * Controller for handling the homepage access.
 * It ensures that only authenticated and verified users can access the
 * homepage.
 * If the user is not authenticated or verified, they are redirected to the
 * login or verification page.
 */
public class HomepageController implements Controller {

	/**
	 * Executes the logic for displaying the homepage.
	 * <p>
	 * If the user is not logged in or not verified, they are redirected to the
	 * login or verification page.
	 * Otherwise, the user is forwarded to the homepage.
	 *
	 * @param request  the HttpServletRequest object containing the request details
	 * @param response the HttpServletResponse object to send the response
	 * @throws ServletException if the request could not be processed
	 * @throws IOException      if an input/output error occurs
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Handling GET request to display the homepage
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			HttpSession session = request.getSession();
			Integer userId = (Integer) session.getAttribute("userId");

			// If user is not logged in, redirect to the login page
			if (userId == null) {
				response.sendRedirect("login");
				return;
			}

			UserDao userDao = new UserDao();

			// If user is not verified, redirect to the verification page
			if (!userDao.isUserVerified(userId)) {
				response.sendRedirect("verify");
				return;
			}

			String username = (String) session.getAttribute("username");

			// If username is missing, redirect to the login page
			if (username == null) {
				response.sendRedirect("login");
				return;
			}

			// Forward the request to the homepage view
			request.getRequestDispatcher("/WEB-INF/views/homepage.jsp").forward(request, response);
		}
	}
}
