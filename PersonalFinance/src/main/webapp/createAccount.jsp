<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <!-- Sidebar -->
    <button class="sidebar-toggle" onclick="toggleSidebar()">☰</button>
    <div class="sidebar" id="sidebar">
        <h1>PFT</h1>
        <nav>
			<a href="dashboard">Dashboard</a>
			<a href="accounts?action=list">Accounts</a>
    		<a href="transactions?action=list">Transactions</a>
    		<a href="goals?action=list">Goals</a>
            <a href="index.jsp">Logout</a>
        </nav>
    </div>

    <!-- Main Content -->
    <main class="dashboard-container">
        <h2 class="welcome-message">Create a New Account</h2>
        <form action="accounts" method="POST">
            <input type="hidden" name="action" value="create">

            <!-- Account Name -->
            <label for="accountName">Account Name:</label>
            <select id="accountName" name="accountName" required>
                <option value="Savings">Savings</option>
                <option value="Checking">Checking</option>
                <option value="Cash">Cash</option>
            </select><br><br>

            <!-- Initial Balance -->
            <label for="balance">Initial Balance:</label>
            <input type="number" id="balance" name="balance" step="0.01" required><br><br>
            
            <!-- Description -->
            <label for="description">Description:</label>
            <input type="text" id="description" name="description" maxlength="255"><br><br>
            
            <!-- Buttons -->
            <a href="accounts?action=list" class="back-button">Cancel</a>
            <button type="submit" class="save-button">Create Account</button>
            
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
