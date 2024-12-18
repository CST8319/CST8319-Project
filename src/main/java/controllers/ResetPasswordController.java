package controllers;

import dao.TokenDao;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;

import java.io.IOException;

/**
 * Controller for handling the password reset process.
 * <p>
 * It handles GET and POST requests for resetting a user's password.
 * The GET request is used to display the reset password form, while the POST
 * request is used to process the form and reset the password.
 */
public class ResetPasswordController implements Controller {

    /**
     * Executes the logic for handling password reset requests.
     * <p>
     * It processes GET and POST requests. The GET request is used to show the reset
     * password form,
     * and the POST request processes the form, validating the reset code and
     * updating the user's password.
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
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Handles the GET request for showing the reset password form.
     * <p>
     * This method retrieves the user's email from the session and passes it to the
     * JSP page for rendering.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email != null) {
            request.setAttribute("email", email);
        }
        request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
    }

    /**
     * Handles the POST request for resetting the user's password.
     * <p>
     * This method validates the reset code, updates the user's password if the code
     * is valid,
     * and deletes the reset token from the database. If the reset code is invalid
     * or missing, an error message is shown.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String resetCode = request.getParameter("reset_code");
        String newPassword = request.getParameter("new_password");

        TokenDao tokendao = new TokenDao();
        UserDao userdao = new UserDao();

        // Validate reset code and new password
        if (resetCode != null && newPassword != null) {
            // Validate the reset code
            if (tokendao.validateResetCode(email, resetCode)) {
                // Update the user's password
                userdao.updatePassword(email, newPassword);
                // Delete the reset code from the database
                tokendao.deleteVerificationToken(email, resetCode, "reset");
                // Notify the user that the password has been reset
                request.setAttribute("message", "Password reset successfully. Please login with your new password.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                // Invalid reset code, show error message
                request.setAttribute("errorMessage", "Invalid reset code.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
            }
        } else {
            // Missing reset code or new password, show error message
            request.setAttribute("errorMessage", "Please provide a reset code and a new password.");
            request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
        }
    }
}
