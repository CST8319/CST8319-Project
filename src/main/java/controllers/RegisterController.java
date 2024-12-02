package controllers;

import services.EmailService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Register;
import dao.UserDao;
import factories.RegistrationFactory;
import dao.TokenDao;

import java.util.UUID;

public class RegisterController implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        }
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8"); // Ensure UTF-8 encoding for parameters
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty()) {
            request.setAttribute("errorMessage", "Email is required.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        UserDao userdao = new UserDao();
        TokenDao tokendao = new TokenDao();
        if (userdao.userCheck(username)) {
            request.setAttribute("errorMessage", "Username already exists.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (userdao.emailCheck(email)) {
            request.setAttribute("errorMessage", "Email already exists.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        Register newUser = RegistrationFactory.createNewUser(email, username, password);
        userdao.newUser(newUser);

        String verificationCode = UUID.randomUUID().toString().substring(0, 4);
        EmailService.sendVerificationEmail(email, verificationCode);
        tokendao.saveVerificationCode(email, verificationCode);

        newUser = userdao.getUser(username);
        if (newUser == null) {
            request.setAttribute("errorMessage", "User registration failed.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("username", newUser.getUsername()); // Store username in session
        session.setAttribute("userId", newUser.getId()); // Store userId in session
        session.setAttribute("email", newUser.getEmail()); // Store email in session
        session.setAttribute("usernameOrEmail", username); // Store usernameOrEmail in session

        request.setAttribute("email", email);
        request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
    }
}
