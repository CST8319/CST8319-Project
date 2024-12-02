package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Controller;
import dao.TokenDao;
import services.EmailService;

public class ResendVerificationController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
		
	}
	
	private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String email = request.getParameter("email");
	        TokenDao tokendao = new TokenDao();
	        
	        String verificationCode = tokendao.getVerificationCode(email);
	        if (verificationCode != null) {
	            EmailService.sendVerificationEmail(email, verificationCode);
	            request.setAttribute("email", email);
	            request.setAttribute("message", "Verification code resent successfully.");
	        } else {
	            request.setAttribute("errorMessage", "Unable to resend verification code.");
	        }
	        request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
    }
}
