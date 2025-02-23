<%@ page import="DAO.JobDAO, DAO.InterviewSlotDAO, models.Job, models.InterviewSlot, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer recruiterId = (Integer) session.getAttribute("userId");
    if (recruiterId == null) {
        response.sendRedirect("login.jsp?msg=Please log in first!");
        return;
    }

    JobDAO jobDAO = new JobDAO();
    InterviewSlotDAO interviewslotDAO = new InterviewSlotDAO();
    List<Job> jobList = jobDAO.getJobsByRecruiter(recruiterId);
    List<InterviewSlot> slotList = interviewslotDAO.getSlotsByRecruiter(recruiterId);

    String msg = request.getParameter("msg");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Recruiter Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        h2 {
            color: #333;
        }

        .nav-links {
            margin-bottom: 20px;
        }

        .nav-links a {
            margin: 0 15px;
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }

        .notification {
            display: <% if (msg != null) { %> block <% } else { %> none <% } %>;
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            text-align: center;
        }

        .job-form {
            background: #e9ecef;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .job-form input, .job-form button {
            margin: 5px;
            padding: 8px;
            width: 90%;
            border: 1px solid #ccc;
            border-radius: 5px;
            display: block;
        }

        .job-form button {
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
        }

        .job-form button:hover {
            background-color: #0056b3;
        }

        .job-list {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
        }

        .job-card {
            background: white;
            border-radius: 8px;
            padding: 15px;
            margin: 10px;
            width: 280px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        .job-card h4 {
            margin: 0;
            color: #333;
        }

        .job-card p {
            font-size: 14px;
            color: #555;
        }

        .job-actions {
            margin-top: 10px;
        }

        .job-actions button {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 5px;
            margin-right: 5px;
        }

        .job-actions button:hover {
            background-color: #bd2130;
        }

        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            width: 300px;
        }

        .close {
            float: right;
            cursor: pointer;
            font-size: 20px;
            color: red;
        }

    </style>
</head>
<body>

    <div class="container">
        <h2>Recruiter Dashboard</h2>

        <div class="nav-links">
            <a href="LogoutServlet">Logout</a> | 
            <a href="InterviewRequestServlet">View Requests</a> |
            <a href="recruiterschedule.jsp">View Schedule</a>
        </div>

        <!-- Success Message -->
        <% if (msg != null) { %>
            <div class="notification"><%= msg %></div>
            <script>
                setTimeout(() => { document.querySelector(".notification").style.display = "none"; }, 3000);
            </script>
        <% } %>

        <div class="job-form">
            <h3>Post a New Job</h3>
            <form action="JobsServlet" method="post">
                <input type="hidden" name="action" value="add">
                <input type="text" name="title" placeholder="Title" required>
                <input type="text" name="description" placeholder="Description" required>
                <input type="text" name="location" placeholder="Location" required>
                <input type="text" name="industry" placeholder="Industry" required>
                <button type="submit">Post Job</button>
            </form>
        </div>

        <h3>Your Job Listings</h3>
        <div class="job-list">
            <% for (Job job : jobList) { %>
                <div class="job-card">
                    <h4><%= job.getTitle() %></h4>
                    <p><strong>Location:</strong> <%= job.getLocation() %></p>
                    <p><strong>Industry:</strong> <%= job.getIndustry() %></p>

                    <div class="job-actions">
                        <form action="JobsServlet" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="jobId" value="<%= job.getId() %>">
                            <button type="submit">Remove</button>
                        </form>
                        <button onclick="openModal(<%= job.getId() %>)">Add Slot</button>
                    </div>

                    <h5>Interview Slots:</h5>
                    <ul>
                        <% for (InterviewSlot slot : slotList) { 
                            if (slot.getJobId() == job.getId()) { %>
                                <li><strong><%= slot.getDateTime() %></strong> - <%= slot.getStatus() %></li>
                            <% } 
                        } %>
                    </ul>
                </div>
            <% } %>
        </div>
    </div>

    <div id="slotModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h3>Set Interview Slot</h3>
            <form action="InterviewSlotServlet" method="post">
                <input type="hidden" id="jobIdInput" name="jobId">
                <input type="datetime-local" name="datetime" required>
                <button type="submit">Save Slot</button>
            </form>
        </div>
    </div>

    <script>
        function openModal(jobId) {
            document.getElementById("jobIdInput").value = jobId;
            document.getElementById("slotModal").style.display = "flex";
        }

        function closeModal() {
            document.getElementById("slotModal").style.display = "none";
        }
    </script>

</body>
</html>