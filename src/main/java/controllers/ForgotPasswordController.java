package controllers;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TokenDao;
import dao.UserDao;
import services.EmailService;

/**
 * Controller for handling the forgot password functionality.
 * It processes the request to initiate the password reset process by
 * verifying the email and sending a reset code to the user.
 */
public class ForgotPasswordController implements Controller {

    /**
     * Executes the forgot password process, handling both GET and POST requests.
     * <p>
     * On GET request, it forwards the user to the forgot password page.
     * On POST request, it verifies the email, generates or retrieves a reset code,
     * and sends a reset password email. If the email doesn't exist, an error
     * message is shown.
     *
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handling GET request to display the forgot password page
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
        }
        // Handling POST request to process the forgot password action
        else if ("POST".equalsIgnoreCase(request.getMethod())) {
            String email = request.getParameter("email");
            UserDao userDao = new UserDao();
            TokenDao tokenDao = new TokenDao();

            // Check if the email exists in the database
            if (userDao.emailCheck(email)) {
                // Retrieve or generate a reset code for the user
                String resetCode = tokenDao.getResetCode(email);
                if (resetCode == null) {
                    resetCode = UUID.randomUUID().toString().substring(0, 4); // Generate a new reset code
                    tokenDao.saveResetCode(email, resetCode); // Save the reset code in the database
                }

                // Send the reset password email to the user
                EmailService.sendResetPasswordEmail(email, resetCode);

                // Store the email in the session and request attributes for later use
                HttpSession session = request.getSession();
                session.setAttribute("email", email); // Set email in session
                request.setAttribute("email", email); // Set email in request

                // Forward to the reset password page
                request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
            } else {
                // If the email does not exist, show an error message
                request.setAttribute("errorMessage", "Email does not exist.");
                request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
            }
        }
    }
}
