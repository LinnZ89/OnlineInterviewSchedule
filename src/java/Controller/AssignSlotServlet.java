package Controller;

import DAO.InterviewRequestDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AssignSlotServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp?msg=Please log in first!");
            return;
        }

        int candidateId = (Integer) session.getAttribute("userId");

        try {
            int slotId = Integer.parseInt(request.getParameter("slotId"));
            int jobId = Integer.parseInt(request.getParameter("jobId")); // Retrieve jobId

            InterviewRequestDAO requestDAO = new InterviewRequestDAO();
            boolean success = requestDAO.requestInterview(candidateId, jobId, slotId);

            if (success) {
                response.sendRedirect("assignslot.jsp?msg=Request submitted. Waiting for recruiter approval.");
            } else {
                response.sendRedirect("candidatedashboard.jsp?msg=Failed to assign slot.");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("candidatedashboard.jsp?msg=Invalid slot selection.");
        }
    }
}
