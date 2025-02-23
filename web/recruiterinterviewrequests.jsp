<%@ page import="java.util.ArrayList, java.util.List, DAO.InterviewRequestDAO, models.InterviewRequest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer recruiterId = (Integer) session.getAttribute("userId");
    if (recruiterId == null) {
        response.sendRedirect("login.jsp?msg=Please log in first!");
        return;
    }

    List<InterviewRequest> interviewrequests = (List<InterviewRequest>) request.getAttribute("interviewrequests");

    if (interviewrequests == null) {
        interviewrequests = new ArrayList<>();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Recruiter Interview Requests</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f8f9fa;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .nav-links {
            text-align: right;
            margin-bottom: 20px;
        }
        .nav-links a {
            margin-right: 15px;
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
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
        .btn {
            padding: 8px 12px;
            border: none;
            color: white;
            cursor: pointer;
            border-radius: 3px;
            margin: 2px;
        }
        .btn-approve {
            background-color: #28a745;
        }
        .btn-approve:hover {
            background-color: #218838;
        }
        .btn-reject {
            background-color: #dc3545;
        }
        .btn-reject:hover {
            background-color: #c82333;
        }
        .finalized {
            font-style: italic;
            color: #888;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Recruiter Interview Requests</h2>

    <div class="nav-links">
        <a href="recruiterdashboard.jsp">Back to Dashboard</a> |
        <a href="LogoutServlet">Logout</a>
    </div>

    <% if (interviewrequests.isEmpty()) { %>
        <p style="text-align: center; font-weight: bold;">No pending interview requests.</p>
    <% } else { %>
        <table>
            <tr>
                <th>Candidate ID</th>
                <th>Job ID</th>
                <th>Slot Date & Time</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% for (InterviewRequest requestObj : interviewrequests) { %>
                <tr>
                    <td><%= requestObj.getCandidateId() %></td>
                    <td><%= requestObj.getJobId() %></td>
                    <td><%= requestObj.getRequestedAt() %></td>
                    <td><%= requestObj.getStatus() %></td>
                    <td>
                        <% if ("PENDING".equals(requestObj.getStatus())) { %>
                            <form action="InterviewRequestServlet" method="post" style="display:inline;">
                                <input type="hidden" name="requestId" value="<%= requestObj.getId() %>">
                                <input type="hidden" name="slotId" value="<%= requestObj.getSlotId() %>">
                                <input type="hidden" name="action" value="approve">
                                <button type="submit" class="btn btn-approve">Approve</button>
                            </form>

                            <form action="InterviewRequestServlet" method="post" style="display:inline;">
                                <input type="hidden" name="requestId" value="<%= requestObj.getId() %>">
                                <input type="hidden" name="slotId" value="<%= requestObj.getSlotId() %>">
                                <input type="hidden" name="action" value="reject">
                                <button type="submit" class="btn btn-reject">Reject</button>
                            </form>
                        <% } else { %>
                            <span class="finalized">Finalized</span>
                        <% } %>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } %>

</div>

</body>
</html>