package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Controller;
import dao.TokenDao;
import services.EmailService;

/**
 * Controller for handling the resend verification code request.
 * <p>
 * It processes POST requests to resend a verification code to the user's email
 * address.
 * If the verification code exists for the email, it is resent via email.
 */
public class ResendVerificationController implements Controller {

	/**
	 * Executes the logic for handling the resend verification code request.
	 * <p>
	 * It processes only POST requests to resend a verification code to the user's
	 * email address.
	 * If the verification code exists, it is sent to the user via email.
	 * If the code does not exist, an error message is displayed.
	 * 
	 * @param request  the HttpServletRequest object containing the request details
	 * @param response the HttpServletResponse object to send the response
	 * @throws ServletException if the request could not be processed
	 * @throws IOException      if an input/output error occurs
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			doPost(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	/**
	 * Handles the POST request for resending the verification code.
	 * <p>
	 * It retrieves the verification code for the provided email and sends it via
	 * email.
	 * If the verification code exists, it is resent successfully. Otherwise, an
	 * error message is shown.
	 * 
	 * @param request  the HttpServletRequest object containing the request details
	 * @param response the HttpServletResponse object to send the response
	 * @throws ServletException if the request could not be processed
	 * @throws IOException      if an input/output error occurs
	 */
	private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		TokenDao tokendao = new TokenDao();

		// Retrieve the verification code associated with the email
		String verificationCode = tokendao.getVerificationCode(email);

		if (verificationCode != null) {
			// Send the verification code to the user's email
			EmailService.sendVerificationEmail(email, verificationCode);
			request.setAttribute("email", email);
			request.setAttribute("message", "Verification code resent successfully.");
		} else {
			// If no verification code is found, set an error message
			request.setAttribute("errorMessage", "Unable to resend verification code.");
		}

		// Forward the request to the verification page
		request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
	}
}
