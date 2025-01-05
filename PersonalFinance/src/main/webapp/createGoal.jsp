<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Goal</title>
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
        <h2 class="welcome-message">Create a New Goal</h2>

        <form action="goals" method="POST" class="form-container">
            <input type="hidden" name="action" value="create">

            <label for="goalName">Goal Name:</label>
            <input type="text" id="goalName" name="goalName" required><br><br>

            <label for="targetAmount">Target Amount:</label>
            <input type="number" id="targetAmount" name="targetAmount" step="0.01" required><br><br>

            <label for="currentAmount">Current Amount:</label>
            <input type="number" id="currentAmount" name="currentAmount" step="0.01" value="0.00" required><br><br>

            <label for="description">Description:</label>
            <input type="text" id="description" name="description" maxlength="255"><br><br>

            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate" required><br><br>

            <a href="goals?action=list" class="back-button">Back</a>
            <button type="submit" class="save-button">Create Goal</button>
        </form>
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
