package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Register;
import dao.FavoritesDao;
import dao.UserDao;
import models.Exercise;

public class LoginController implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            String usernameOrEmail = request.getParameter("username_or_email");
            String password = request.getParameter("password");
            UserDao userdao = new UserDao();

            if (userdao.validateUser(usernameOrEmail, password)) {
                Register user = userdao.getUser(usernameOrEmail);

                HttpSession session = request.getSession();
                session.setAttribute("username", user.getUsername()); // Store username in session
                session.setAttribute("userId", user.getId()); // Store userId in session
                session.setAttribute("email", user.getEmail());
                
                //store favorites into session on login
                FavoritesDao favoriteDao = new FavoritesDao();
        		HashMap<Integer, Exercise> favorites = favoriteDao.displayFavorites(user.getId()); //Dao ensures not null
        
        		session.setAttribute("favorites", favorites);

                if (!userdao.isUserVerified(usernameOrEmail)) {
                    response.sendRedirect("verify");
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/homepage.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username/email or password.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        }

    }

}
