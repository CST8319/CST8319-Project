package controllers;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Register;
import dao.TokenDao;
import dao.UserDao;
import services.EmailService;

/**
 * Controller for handling user email verification.
 * <p>
 * This controller processes both GET and POST requests for verifying a user's
 * email address.
 * The GET request is used to display the verification form, while the POST
 * request is used to process the verification code and mark the user as
 * verified.
 */
public class VerifyController implements Controller {

	/**
	 * Executes the appropriate method based on the HTTP request method (GET or
	 * POST).
	 * <p>
	 * The method checks if the request is GET or POST and calls the respective
	 * handler method.
	 * If the method is neither GET nor POST, it sends a 405 Method Not Allowed
	 * error.
	 * 
	 * @param request  the HttpServletRequest object containing the request details
	 * @param response the HttpServletResponse object to send the response
	 * @throws ServletException if the request could not be processed
	 * @throws IOException      if an input/output error occurs
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			handleGetRequest(request, response);
		} else if ("POST".equalsIgnoreCase(request.getMethod())) {
			handlePostRequest(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	/**
	 * Handles the GET request to display the verification form.
	 * <p>
	 * This method retrieves the user's email from the session, validates it, and
	 * passes it to the JSP page for rendering the verification form.
	 * If the user is not logged in or does not have a valid email, they are
	 * redirected to the login page.
	 * 
	 * @param request  the HttpServletRequest object containing the request details
	 * @param response the HttpServletResponse object to send the response
	 * @throws ServletException if the request could not be processed
	 * @throws IOException      if an input/output error occurs
	 */
	private void handleGetRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			response.sendRedirect("login");
			return;
		}

		String usernameOrEmail = (String) session.getAttribute("usernameOrEmail");
		if (usernameOrEmail == null) {
			response.sendRedirect("login");
			return;
		}

		UserDao userDao = new UserDao();
		Register user = userDao.getUser(usernameOrEmail);
		if (user == null) {
			response.sendRedirect("login");
			return;
		}

		String email = user.getEmail();
		session.setAttribute("email", email);

		request.setAttribute("email", email);
		request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
	}

	/**
	 * Handles the POST request to verify the user's email.
	 * <p>
	 * This method processes the verification code submitted by the user. If the
	 * code is valid, the user is marked as verified,
	 * and the verification token is deleted. The user is then redirected to the
	 * homepage. If the code is invalid,
	 * an error message is displayed. If the user requests to resend the
	 * verification code, a new code is generated and sent to the user's email.
	 * 
	 * @param request  the HttpServletRequest object containing the request details
	 * @param response the HttpServletResponse object to send the response
	 * @throws ServletException if the request could not be processed
	 * @throws IOException      if an input/output error occurs
	 */
	private void handlePostRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String verificationCode = request.getParameter("verification_code");
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			response.sendRedirect("login");
			return;
		}

		String email = (String) session.getAttribute("email");
		if (email == null) {
			String usernameOrEmail = (String) session.getAttribute("usernameOrEmail");
			if (usernameOrEmail == null) {
				response.sendRedirect("login");
				return;
			}

			UserDao userDao = new UserDao();
			Register user = userDao.getUser(usernameOrEmail);
			if (user == null) {
				response.sendRedirect("login");
				return;
			}
			email = user.getEmail();
			session.setAttribute("email", email);
		}

		TokenDao tokenDao = new TokenDao();

		// Handle resend verification code
		if ("true".equals(request.getParameter("resend"))) {
			String newVerificationCode = UUID.randomUUID().toString().substring(0, 4);
			tokenDao.saveVerificationCode(email, newVerificationCode);
			EmailService.sendVerificationEmail(email, newVerificationCode);

			request.setAttribute("email", email);
			request.setAttribute("message", "Verification code resent successfully.");
			request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
		} else {
			// Handle verification code validation
			if (tokenDao.validateVerificationCode(email, verificationCode)) {
				UserDao userDao = new UserDao();
				userDao.markUserAsVerified(email); // Mark the user as verified
				tokenDao.deleteVerificationToken(email, verificationCode, "verification"); // Delete the verification
																							// token

				session.setAttribute("isVerified", true);

				Register user = userDao.getUser(email);
				session.setAttribute("username", user.getUsername()); // Store username in session
				session.setAttribute("userId", user.getId()); // Store userId in session
				session.setAttribute("email", user.getEmail()); // Store email in session

				response.sendRedirect("homepage");
			} else {
				// Invalid verification code, show error message
				request.setAttribute("errorMessage", "Invalid verification code.");
				request.setAttribute("email", email);
				request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
			}
		}
	}
}
