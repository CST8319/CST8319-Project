package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;

/*
 * Handles forwarding to the homepage during log in. If there are problems with the userid, username or verification 
 * the user will not be able to access the homepage and redirected back to login or verification.
 * 
 */

public class HomepageController implements Controller{
    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			HttpSession session = request.getSession();
	        Integer userId = (Integer) session.getAttribute("userId");
	        
	        if (userId == null) {
	            response.sendRedirect("login");
	            return;
	        }
	        
	        UserDao userdao = new UserDao();
	      
	        if (!userdao.isUserVerified(userId)) {
	            response.sendRedirect("verify");
	            return;
	        }
	        
	        String username = (String) session.getAttribute("username");
	        
	        if (username == null) {
	            response.sendRedirect("login");
	            return;
	        }
	        
	        request.getRequestDispatcher("/WEB-INF/views/homepage.jsp").forward(request, response);
        }
		
	}

}
