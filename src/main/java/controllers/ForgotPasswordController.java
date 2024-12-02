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

public class ForgotPasswordController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
        }else if("POST".equalsIgnoreCase(request.getMethod())) {
        	String email = request.getParameter("email");
            UserDao userDao = new UserDao();
            TokenDao tokenDao = new TokenDao();

            if (userDao.emailCheck(email)) {
                String resetCode = tokenDao.getResetCode(email);
                if (resetCode == null) {
                    resetCode = UUID.randomUUID().toString().substring(0, 4);
                    tokenDao.saveResetCode(email, resetCode);
                }
                
                EmailService.sendResetPasswordEmail(email, resetCode);
                HttpSession session = request.getSession();
                session.setAttribute("email", email); // Set email in session
                request.setAttribute("email", email); // Set email in request
                request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Email does not exist.");
                request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
            }
        }
		
	}

}
