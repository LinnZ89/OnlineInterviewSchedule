package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DAO.UserDAO;
import models.User;

public class LoginServlet extends HttpServlet {
    
    private final String LOGIN_VIEW = "login.jsp"; 
    private final String RECRUITER_DASHBOARD = "recruiterdashboard.jsp";
    private final String CANDIDATE_DASHBOARD = "candidatedashboard.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if ("RECRUITER".equals(user.getRole())) {
                response.sendRedirect(RECRUITER_DASHBOARD);
            } else {
                response.sendRedirect(CANDIDATE_DASHBOARD);
            }
            return;
        }
        request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userdao = new UserDAO();
        User user = userdao.authenticateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            session.setAttribute("userId", user.getId());

            if ("RECRUITER".equals(user.getRole())) {
                response.sendRedirect(RECRUITER_DASHBOARD);
            } else {
                response.sendRedirect(CANDIDATE_DASHBOARD);
            }
        } else {
            request.setAttribute("msg", "Invalid credentials");
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
        }
    }
}
