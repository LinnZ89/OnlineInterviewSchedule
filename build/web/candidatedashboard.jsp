<%@ page import="DAO.JobDAO, DAO.InterviewSlotDAO, models.Job, models.InterviewSlot, java.util.List, java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer candidateId = (Integer) session.getAttribute("userId");
    if (candidateId == null) {
        response.sendRedirect("login.jsp?msg=Please log in first!");
        return;
    }

    JobDAO jobDAO = new JobDAO();
    InterviewSlotDAO interviewSlotDAO = new InterviewSlotDAO();

    String keyword = request.getParameter("keyword");
    String selectedLocation = request.getParameter("location");
    String selectedIndustry = request.getParameter("industry");

    Set<String> locations = jobDAO.getAllLocations();
    Set<String> industries = jobDAO.getAllIndustries();

    List<Job> jobList = jobDAO.searchJobs(keyword, selectedLocation, selectedIndustry);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Candidate Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f9f9f9;
        }
        h2 {
            color: #333;
            text-align: center;
        }
        a {
            margin-right: 15px;
            text-decoration: none;
            color: #007BFF;
        }
        a:hover {
            text-decoration: underline;
        }
        .container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
            border-radius: 5px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #007BFF;
            color: white;
        }
        .btn {
            padding: 5px 10px;
            border: none;
            background-color: #007BFF;
            color: white;
            cursor: pointer;
            border-radius: 3px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .filter-section {
            margin-bottom: 20px;
            text-align: center;
        }
        .filter-section label, .filter-section select, .filter-section input {
            margin-right: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Candidate Dashboard</h2>
    <div style="text-align: right;">
        <a href="candidateschedule.jsp">View Schedule</a> | 
        <a href="LogoutServlet">Logout</a>
    </div>

    <h3>Find Jobs</h3>
    <form method="get" class="filter-section">
        <label>Keyword:</label>
        <input type="text" name="keyword" placeholder="Title/Description" value="<%= (keyword != null) ? keyword : "" %>">
        
        <label>Location:</label>
        <select name="location">
            <option value="">All Locations</option>
            <% for (String loc : locations) { %>
                <option value="<%= loc %>" <%= (loc.equals(selectedLocation)) ? "selected" : "" %>><%= loc %></option>
            <% } %>
        </select>

        <label>Industry:</label>
        <select name="industry">
            <option value="">All Industries</option>
            <% for (String ind : industries) { %>
                <option value="<%= ind %>" <%= (ind.equals(selectedIndustry)) ? "selected" : "" %>><%= ind %></option>
            <% } %>
        </select>

        <button type="submit" class="btn">Search</button>
    </form>

    <h3>Available Jobs</h3>
    <table>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Location</th>
            <th>Industry</th>
            <th>Available Slots</th>
            <th>Action</th>
        </tr>

        <% for (Job job : jobList) { 
            List<InterviewSlot> availableSlots = interviewSlotDAO.getAvailableSlotsForJob(job.getId(), candidateId);
        %>
        <tr>
            <td><%= job.getTitle() %></td>
            <td><%= job.getDescription() %></td>
            <td><%= job.getLocation() %></td>
            <td><%= job.getIndustry() %></td>
            <td>
                <% if (availableSlots.isEmpty()) { %>
                    <i>No available slots</i>
                <% } else { %>
                    <form action="AssignSlotServlet" method="post">
                        <input type="hidden" name="jobId" value="<%= job.getId() %>">
                        <label for="slotId_<%= job.getId() %>">Select Slot:</label>
                        <select name="slotId" id="slotId_<%= job.getId() %>">
                            <% for (InterviewSlot slot : availableSlots) { %>
                                <option value="<%= slot.getId() %>">
                                    <%= slot.getDateTime() %>
                                </option>
                            <% } %>
                        </select>
                        <button type="submit" class="btn">Assign Slot</button>
                    </form>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
</div>

</body>
</html>
