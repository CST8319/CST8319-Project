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

public class VerifyController implements Controller{

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
	
	private void handleGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	private void handlePostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	        if ("true".equals(request.getParameter("resend"))) {
	            String newVerificationCode = UUID.randomUUID().toString().substring(0, 4);
	            tokenDao.saveVerificationCode(email, newVerificationCode);
	            EmailService.sendVerificationEmail(email, newVerificationCode);

	            request.setAttribute("email", email);
	            request.setAttribute("message", "Verification code resent successfully.");
	            request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
	        } else {
	            if (tokenDao.validateVerificationCode(email, verificationCode)) {
	                UserDao userDao = new UserDao();
	                userDao.markUserAsVerified(email); // Mark the user as verified
	                tokenDao.deleteVerificationToken(email, verificationCode, "verification"); // Delete the verification token
	                
	                session.setAttribute("isVerified", true);
	                
	                Register user = userDao.getUser(email);
	                session.setAttribute("username", user.getUsername()); // Store username in session
	                session.setAttribute("userId", user.getId()); // Store userId in session
	                session.setAttribute("email", user.getEmail()); // Store email in session
	                
	                response.sendRedirect("homepage");
	            } else {
	                request.setAttribute("errorMessage", "Invalid verification code.");
	                request.setAttribute("email", email);
	                request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
	            }
	        }
	    }

}
