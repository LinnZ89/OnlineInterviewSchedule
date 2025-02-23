package Controller;

import DAO.InterviewRequestDAO;
import models.InterviewRequest;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InterviewRequestServlet extends HttpServlet {

    private InterviewRequestDAO interviewRequestDAO = new InterviewRequestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp?msg=Please log in first!");
            return;
        }

        int recruiterId = (Integer) session.getAttribute("userId");

        List<InterviewRequest> requests = interviewRequestDAO.getRequestsForRecruiter(recruiterId);

        request.setAttribute("interviewrequests", requests);
        request.getRequestDispatcher("recruiterinterviewrequests.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String requestIdStr = request.getParameter("requestId");
        String slotIdStr = request.getParameter("slotId");

        if (action == null || requestIdStr == null || slotIdStr == null) {
            System.out.println("ERROR: Missing requestId or slotId");
            response.sendRedirect("recruiterinterviewrequests.jsp?msg=Invalid request ID or slot ID!");
            return;
        }

        try {
            int requestId = Integer.parseInt(requestIdStr);
            int slotId = Integer.parseInt(slotIdStr);

            if ("approve".equals(action)) {
                boolean success = interviewRequestDAO.approveRequest(requestId, slotId);
                if (success) {
                    response.sendRedirect("recruiterinterviewrequests.jsp?msg=Request approved successfully!");
                } else {
                    response.sendRedirect("recruiterinterviewrequests.jsp?msg=Approval failed!");
                }
            } else if ("reject".equals(action)) {
                boolean success = interviewRequestDAO.rejectRequest(requestId);
                if (success) {
                    response.sendRedirect("recruiterinterviewrequests.jsp?msg=Request rejected successfully!");
                } else {
                    response.sendRedirect("recruiterinterviewrequests.jsp?msg=Rejection failed!");
                }
            } else {
                response.sendRedirect("recruiterinterviewrequests.jsp?msg=Unknown action!");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("recruiterinterviewrequests.jsp?msg=Invalid request ID or slot ID!");
        }
    }
}
