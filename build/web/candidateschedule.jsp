<%@ page import="DAO.InterviewRequestDAO, models.InterviewRequest, DAO.JobDAO, models.Job, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer candidateId = (Integer) session.getAttribute("userId");
    if (candidateId == null) {
        response.sendRedirect("login.jsp?msg=Please log in first!");
        return;
    }

    InterviewRequestDAO interviewRequestDAO = new InterviewRequestDAO();
    JobDAO jobDAO = new JobDAO();

    List<InterviewRequest> approvedRequests = interviewRequestDAO.getApprovedRequestsForCandidate(candidateId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Candidate Interview Schedule</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f8f9fa;
            text-align: center;
        }
        h2 {
            color: #333;
        }
        .nav-links {
            margin-bottom: 20px;
        }
        .nav-links a {
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
            margin: 0 10px;
        }
        .nav-links a:hover {
            text-decoration: underline;
        }
        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #007BFF;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .no-data {
            text-align: center;
            font-style: italic;
            color: gray;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Candidate Interview Schedule</h2>

    <div class="nav-links">
        <a href="candidatedashboard.jsp">Back to Dashboard</a> |
        <a href="LogoutServlet">Logout</a>
    </div>

    <h3>Approved Interview Slots</h3>

    <% if (approvedRequests.isEmpty()) { %>
        <p class="no-data">No confirmed interviews scheduled yet.</p>
    <% } else { %>
        <table>
            <tr>
                <th>Job Title</th>
                <th>Job Location</th>
                <th>Interview Date & Time</th>
            </tr>
            <% for (InterviewRequest interviewReq : approvedRequests) {  
                Job job = jobDAO.getJobById(interviewReq.getJobId()); // Fetch job details
            %>
            <tr>
                <td><%= (job != null) ? job.getTitle() : "Unknown" %></td>
                <td><%= (job != null) ? job.getLocation() : "Unknown" %></td>
                <td><%= interviewReq.getRequestedAt() %></td>
            </tr>
            <% } %>
        </table>
    <% } %>

</div>

</body>
</html>
