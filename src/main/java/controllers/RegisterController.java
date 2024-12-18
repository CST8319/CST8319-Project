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

/**
 * Controller for handling user registration.
 * It processes both GET and POST requests for registering a new user.
 * <p>
 * On GET request, it displays the registration form.
 * On POST request, it validates the input, registers the user, and sends a
 * verification email.
 */
public class RegisterController implements Controller {

    /**
     * Executes the logic for handling user registration requests.
     * <p>
     * On GET request, it displays the registration form.
     * On POST request, it validates the input, registers the user, and sends a
     * verification email.
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
     * Handles the GET request for displaying the registration form.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    /**
     * Handles the POST request for processing the registration form.
     * It validates the user input, checks for existing username and email, creates
     * a new user,
     * sends a verification email, and redirects to the verification page.
     * 
     * @param request  the HttpServletRequest object containing the request details
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if the request could not be processed
     * @throws IOException      if an input/output error occurs
     */
    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Ensure UTF-8 encoding for parameters
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if email is provided
        if (email == null || email.isEmpty()) {
            request.setAttribute("errorMessage", "Email is required.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        UserDao userdao = new UserDao();
        TokenDao tokendao = new TokenDao();

        // Check if username already exists
        if (userdao.userCheck(username)) {
            request.setAttribute("errorMessage", "Username already exists.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Check if email already exists
        if (userdao.emailCheck(email)) {
            request.setAttribute("errorMessage", "Email already exists.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Create a new user and save to database
        Register newUser = RegistrationFactory.createNewUser(email, username, password);
        userdao.newUser(newUser);

        // Generate and send verification email
        String verificationCode = UUID.randomUUID().toString().substring(0, 4);
        EmailService.sendVerificationEmail(email, verificationCode);
        tokendao.saveVerificationCode(email, verificationCode);

        // Retrieve the newly created user
        newUser = userdao.getUser(username);
        if (newUser == null) {
            request.setAttribute("errorMessage", "User registration failed.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Store user information in session
        HttpSession session = request.getSession();
        session.setAttribute("username", newUser.getUsername()); // Store username in session
        session.setAttribute("userId", newUser.getId()); // Store userId in session
        session.setAttribute("email", newUser.getEmail()); // Store email in session
        session.setAttribute("usernameOrEmail", username); // Store usernameOrEmail in session

        // Forward to the verification page
        request.setAttribute("email", email);
        request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
    }
}
