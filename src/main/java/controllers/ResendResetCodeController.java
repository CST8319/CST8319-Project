package controllers;

import dao.UserDao;
import dao.TokenDao;
import services.EmailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

/**
 * Controller for handling the resend password reset code request.
 * <p>
 * It processes POST requests to resend a password reset code to the user's
 * email address.
 * If the email exists, a reset code is either retrieved or generated, saved,
 * and sent to the user.
 */
public class ResendResetCodeController implements Controller {

    /**
     * Executes the logic for handling the resend password reset code request.
     * <p>
     * It processes only POST requests to resend a reset code to the user's email
     * address.
     * If the email is valid, the reset code is sent to the user via email.
     * If the email does not exist, an error message is displayed.
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
     * Handles the POST request for resending the password reset code.
     * <p>
     * It checks if the provided email exists, retrieves or generates a reset code,
     * saves it, and sends it to the user via email. If the email does not exist,
     * an error message is displayed.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDao userdao = new UserDao();
        TokenDao tokendao = new TokenDao();

        // Check if the email exists in the system
        if (userdao.emailCheck(email)) {
            // Retrieve or generate a new reset code
            String resetCode = tokendao.getResetCode(email);
            if (resetCode == null) {
                resetCode = UUID.randomUUID().toString().substring(0, 4);
                tokendao.saveResetCode(email, resetCode);
            }

            // Send the reset code to the user's email
            EmailService.sendResetPasswordEmail(email, resetCode);

            // Set success message and forward to resetPassword.jsp
            request.setAttribute("email", email);
            request.setAttribute("message", "Reset code resent successfully.");
        } else {
            // Set error message if email does not exist
            request.setAttribute("errorMessage", "Email does not exist.");
        }

        // Forward the request to the reset password page
        request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
    }
}
