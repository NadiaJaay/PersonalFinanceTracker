<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.PersonalFinance.beans.Goal" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Finance | Goals</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <!-- Sidebar -->
    <button class="sidebar-toggle" onclick="toggleSidebar()">☰</button>
    <div class="sidebar" id="sidebar">
        <h1>PFT</h1>
        <nav>
            <a href="dashboard.jsp">Dashboard</a>
            <a href="accounts?action=list">Accounts</a>
            <a href="transactions.jsp">Transactions</a>
            <a href="goals?action=list">Goals</a>
            <a href="index.jsp">Logout</a>
        </nav>
    </div>

    <!-- Main Content -->
    <main class="dashboard-container">
        <h2 class="welcome-message">Your Goals</h2>

        <!-- Dropdown Filter -->
        <form method="GET" action="goals" class="filter-form">
            <input type="hidden" name="action" value="list">
            <label for="status">Filter by Status:</label>
            <select id="status" name="status" onchange="this.form.submit()" class="dropdown-filter">
                <option value="pending" <%= "pending".equals(request.getParameter("status")) ? "selected" : "" %>>Pending Goals</option>
                <option value="completed" <%= "completed".equals(request.getParameter("status")) ? "selected" : "" %>>Completed Goals</option>
            </select>
        </form>

        <!-- Add New Goal Button -->
        <a href="createGoal.jsp" class="add-new-account">Add New Goal</a>

        <!-- Display Goals -->
        <div class="card-container">
            <%
                List<Goal> goals = (List<Goal>) request.getAttribute("goals");

                if (goals != null && !goals.isEmpty()) {
                    for (Goal goal : goals) {
            %>
                        <div class="book-card">
                            <h3><%= goal.getGoalName() %> Goal</h3>
                            <p><strong>Target Amount:</strong> $<%= String.format("%.2f", goal.getTargetAmount()) %></p>
                            <p><strong>Current Amount:</strong> $<%= String.format("%.2f", goal.getCurrentAmount()) %></p>
                            <p><strong>Description:</strong> <%= (goal.getDescription() != null ? goal.getDescription() : "No description provided") %></p>
                            <p><strong>End Date:</strong> <%= goal.getEndDate() %></p>
                            <p><strong>Status:</strong> <%= goal.getIsComplete() ? "Complete" : "In Progress" %></p>
                            <div class="card-actions">
                                <% if (!goal.getIsComplete()) { %>
                                    <a href="goals?action=markComplete&id=<%= goal.getGoal_id() %>" class="btn-edit">Mark Complete</a>
                                <% } %>
                                <a href="goals?action=edit&id=<%= goal.getGoal_id() %>" class="btn-edit">Edit</a>
                                <a href="goals?action=delete&id=<%= goal.getGoal_id() %>" class="btn-delete">Delete</a>
                            </div>
                        </div>
            <%
                    }
                } else {
            %>
                <p>No goals found for the selected filter.</p>
            <%
                }
            %>
        </div>
    </main>

    <footer class="footer">
        <p>&copy; 2025 Personal Finance Tracker</p>
        <p>Created by Nadia Jujooste</p>
    </footer>

    <script>
        function toggleSidebar() {
            document.getElementById('sidebar').classList.toggle('active');
        }
    </script>
</body>
</html>
