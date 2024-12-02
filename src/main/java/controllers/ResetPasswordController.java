package controllers;

import dao.TokenDao;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;

import java.io.IOException;

public class ResetPasswordController implements Controller {

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

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email != null) {
            request.setAttribute("email", email);
        }
        request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String resetCode = request.getParameter("reset_code");
        String newPassword = request.getParameter("new_password");

        TokenDao tokendao = new TokenDao();
        UserDao userdao = new UserDao();

        if (resetCode != null && newPassword != null) {
            if (tokendao.validateResetCode(email, resetCode)) {
                userdao.updatePassword(email, newPassword);
                tokendao.deleteVerificationToken(email, resetCode, "reset");
                request.setAttribute("message", "Password reset successfully. Please login with your new password.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Invalid reset code.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Please provide a reset code and a new password.");
            request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
        }
    }
}
