<%@ page import="DAO.InterviewRequestDAO, DAO.JobDAO, models.InterviewRequest, models.Job, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer candidateId = (Integer) session.getAttribute("userId");
    if (candidateId == null) {
        response.sendRedirect("login.jsp?msg=Please log in first!");
        return;
    }

    InterviewRequestDAO requestDAO = new InterviewRequestDAO();
    JobDAO jobDAO = new JobDAO();

    List<InterviewRequest> requests = requestDAO.getRequestsForCandidate(candidateId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assigned Slots</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
            margin: 0;
        }

        .container {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
            width: 90%;
            max-width: 800px;
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        /* Navigation Links */
        .nav {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
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

        /* Table Styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
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

        /* Status Styles */
        .status-confirmed {
            color: #28a745;
            font-weight: bold;
        }

        .status-declined {
            color: #dc3545;
            font-weight: bold;
        }

        .status-pending {
            color: #ffc107;
            font-weight: bold;
        }

        .no-data {
            color: gray;
            font-style: italic;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Your Interview Requests</h2>

        <div class="nav">
            <a href="candidatedashboard.jsp">Back to Dashboard</a>
            <a href="LogoutServlet">Logout</a>
        </div>

        <% if (requests.isEmpty()) { %>
            <p class="no-data">No interview requests yet.</p>
        <% } else { %>
            <table>
                <tr>
                    <th>Job Title</th>
                    <th>Location</th>
                    <th>Interview Date & Time</th>
                    <th>Status</th>
                </tr>

                <% for (InterviewRequest req : requests) { 
                    Job job = jobDAO.getJobById(req.getJobId());
                %>
                <tr>
                    <td><%= (job != null) ? job.getTitle() : "N/A" %></td>
                    <td><%= (job != null) ? job.getLocation() : "N/A" %></td>
                    <td><%= req.getRequestedAt() %></td>
                    <td>
                        <% if ("CONFIRMED".equals(req.getStatus())) { %>
                            <span class="status-confirmed">✅ Confirmed</span>
                        <% } else if ("DECLINED".equals(req.getStatus())) { %>
                            <span class="status-declined">❌ Declined</span>
                        <% } else { %>
                            <span class="status-pending">⏳ Pending</span>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </table>
        <% } %>
    </div>

</body>
</html>
