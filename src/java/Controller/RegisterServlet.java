package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.UserDAO;
import models.User;


public class RegisterServlet extends HttpServlet {

    private final String LOGIN_VIEW = "login.jsp"; 
    private final String REGISTER_VIEW = "register.jsp"; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(REGISTER_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            role == null || role.isEmpty()) {
            response.sendRedirect(REGISTER_VIEW + "?msg=All fields are required!");
            return;
        }

        User user = new User(username, password, role);
        UserDAO userdao = new UserDAO();

        if (userdao.isUsernameTaken(username)) {
            response.sendRedirect(REGISTER_VIEW + "?msg=Username already taken!");
            return;
        }

        if (userdao.registerUser(user)) {
            response.sendRedirect(LOGIN_VIEW + "?msg=Registration successful! Please log in.");
        } else {
            response.sendRedirect(REGISTER_VIEW + "?msg=Registration failed! Please try again.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user registration";
    }
}
