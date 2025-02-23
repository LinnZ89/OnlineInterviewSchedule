package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DAO.JobDAO;
import models.Job;
import java.util.List;

public class JobsServlet extends HttpServlet {
    private final String LOGIN_VIEW = "login.jsp";
    private final String RECRUITER_DASHBOARD = "recruiterdashboard.jsp";
    private final String CANDIDATE_DASHBOARD = "candidatedashboard.jsp";

    
    private JobDAO jobDAO = new JobDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(LOGIN_VIEW + "?msg=Please log in first!");
            return;
        }

        String role = (String) session.getAttribute("role");

        if ("RECRUITER".equals(role)) {
            listRecruiterJobs(request, response);
        } else {
            listCandidateJobs(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(RECRUITER_DASHBOARD + "?msg=Invalid action!");
            return;
        }

        switch (action) {
            case "add":
                addJob(request, response);
                break;
            case "remove":
                removeJob(request, response);
                break;
            default:
                response.sendRedirect(RECRUITER_DASHBOARD + "?msg=Invalid action!");
        }
    }

    private void listRecruiterJobs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int recruiterId = (Integer) request.getSession().getAttribute("userId");
        List<Job> jobList = jobDAO.getJobsByRecruiter(recruiterId);

        request.setAttribute("jobList", jobList);
        request.getRequestDispatcher(RECRUITER_DASHBOARD).forward(request, response);
    }

    private void listCandidateJobs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String location = request.getParameter("location");
        String industry = request.getParameter("industry");

        List<Job> jobList = jobDAO.searchJobs(keyword, location, industry);
        request.setAttribute("jobList", jobList);
        request.getRequestDispatcher(CANDIDATE_DASHBOARD).forward(request, response);
    }

    private void addJob(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null || !"RECRUITER".equals(session.getAttribute("role"))) {
            response.sendRedirect(LOGIN_VIEW + "?msg=Unauthorized access!");
            return;
        }

        int recruiterId = (Integer) session.getAttribute("userId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String industry = request.getParameter("industry");

        if (title == null || description == null || location == null || industry == null ||
            title.isEmpty() || description.isEmpty() || location.isEmpty() || industry.isEmpty()) {
            response.sendRedirect(RECRUITER_DASHBOARD + "?msg=All fields are required!");
            return;
        }

        Job job = new Job(recruiterId, title, description, location, industry);
        boolean success = jobDAO.addJob(job);

        if (success) {
            response.sendRedirect(RECRUITER_DASHBOARD + "?msg=Job added successfully!");
        } else {
            response.sendRedirect(RECRUITER_DASHBOARD + "?msg=Failed to add job.");
        }
    }

    private void removeJob(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null || !"RECRUITER".equals(session.getAttribute("role"))) {
            response.sendRedirect(LOGIN_VIEW + "?msg=Unauthorized access!");
            return;
        }

        int recruiterId = (Integer) session.getAttribute("userId");
        int jobId;
        
        try {
            jobId = Integer.parseInt(request.getParameter("jobId"));
        } catch (NumberFormatException e) {
            response.sendRedirect(RECRUITER_DASHBOARD + "?msg=Invalid job ID!");
            return;
        }

        boolean success = jobDAO.deleteJob(jobId);
        if (success) {
            response.sendRedirect(RECRUITER_DASHBOARD + "?msg=Job removed successfully!");
        } else {
            response.sendRedirect(RECRUITER_DASHBOARD + "?msg=Failed to remove job.");
        }
    }
}
