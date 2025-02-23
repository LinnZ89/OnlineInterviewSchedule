<%@ page import="DAO.InterviewRequestDAO, models.InterviewRequest, DAO.JobDAO, models.Job, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer recruiterId = (Integer) session.getAttribute("userId");
    if (recruiterId == null) {
        response.sendRedirect("login.jsp?msg=Please log in first!");
        return;
    }

    InterviewRequestDAO interviewRequestDAO = new InterviewRequestDAO();
    JobDAO jobDAO = new JobDAO();

    List<InterviewRequest> approvedRequests = interviewRequestDAO.getApprovedRequestsForRecruiter(recruiterId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recruiter Interview Schedule</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 900px;
            margin: 40px auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        h2, h3 {
            text-align: center;
            color: #333;
        }
        .nav {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .nav a {
            text-decoration: none;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
            transition: background 0.3s;
        }
        .nav a:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .no-data {
            text-align: center;
            font-style: italic;
            color: gray;
            margin-top: 10px;
        }
        footer {
            text-align: center;
            margin-top: 20px;
            padding: 10px;
            background: #4CAF50;
            color: white;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Recruiter Interview Schedule</h2>

    <div class="nav">
        <a href="recruiterdashboard.jsp">Back to Dashboard</a>
        <a href="LogoutServlet">Logout</a>
    </div>

    <h3>Approved Interview Slots</h3>

    <% if (approvedRequests.isEmpty()) { %>
        <p class="no-data">No confirmed interviews scheduled.</p>
    <% } else { %>
        <table>
            <tr>
                <th>Candidate ID</th>
                <th>Job Title</th>
                <th>Job Location</th>
                <th>Interview Date & Time</th>
            </tr>
            <% for (InterviewRequest interviewReq : approvedRequests) {  
                Job job = jobDAO.getJobById(interviewReq.getJobId()); // Fetch job details
            %>
            <tr>
                <td><%= interviewReq.getCandidateId() %></td>
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
