package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Profile;
import beans.ProfileBuilder;
import dao.ProfileDao;

public class ProfileController implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        }
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login");
        } else {
            ProfileDao dao = new ProfileDao();
            Profile profile = dao.getProfileByUserId(userId);
            if (profile != null) {
                request.setAttribute("firstName", profile.getFirstName());
                request.setAttribute("lastName", profile.getLastName());
                request.setAttribute("age", profile.getAge());
                request.setAttribute("gender", profile.getGender());
                request.setAttribute("weight", profile.getWeight());
                request.setAttribute("height", profile.getHeight());
            }
            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
        }
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login");
        } else {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int age = parseOrDefault(request.getParameter("age"), 0);
            String gender = request.getParameter("gender");
            int weight = parseOrDefault(request.getParameter("weight"), 0);
            int height = parseOrDefault(request.getParameter("height"), 0);

            Profile profile = new ProfileBuilder(userId)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age)
                    .setGender(gender)
                    .setWeight(weight)
                    .setHeight(height)
                    .build();

            ProfileDao dao = new ProfileDao();
            dao.saveOrUpdateProfile(profile);

            request.setAttribute("message", "Profile updated successfully.");
            doGet(request, response);
        }
    }

    private int parseOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
