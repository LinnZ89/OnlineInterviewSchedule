package Controller;

import DAO.InterviewSlotDAO;
import models.InterviewSlot;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InterviewSlotServlet extends HttpServlet {

    private InterviewSlotDAO slotDAO = new InterviewSlotDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp?msg=Unauthorized access!");
            return;
        }

        int recruiterId = (Integer) session.getAttribute("userId");
        int jobId = Integer.parseInt(request.getParameter("jobId"));
        String datetime = request.getParameter("datetime");

        try {
            Timestamp slotTime = Timestamp.valueOf(datetime.replace("T", " ") + ":00");
            InterviewSlot newSlot = new InterviewSlot(0, recruiterId, null, jobId, slotTime, "AVAILABLE");

            boolean success = slotDAO.addInterviewSlot(newSlot);

            if (success) {
                response.sendRedirect("recruiterdashboard.jsp?msg=Slot added successfully!");
            } else {
                response.sendRedirect("recruiterdashboard.jsp?msg=Failed to add slot.");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect("recruiterdashboard.jsp?msg=Invalid date format.");
        }
    }
}
