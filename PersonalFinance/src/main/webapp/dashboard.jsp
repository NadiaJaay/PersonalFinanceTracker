<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Finance | Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">

</head>
<body class="background">

    <!-- Sidebar Toggle Button -->
    <button class="sidebar-toggle" onclick="toggleSidebar()">☰</button>

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <h1>PFT</h1>
        <nav>
            <a href="dashboard.jsp">Dashboard</a>
			<a href="accounts?action=list">Accounts</a>
            <a href="transactions.jsp">Transactions</a>
            <a href="goals.jsp">Goals</a>
            <a href="index.jsp">Logout</a>
        </nav>
    </div>

    <!-- Main Content -->
    <main class="dashboard-container">
        <h2 class="welcome-message">Your Dashboard</h2>
        <p>Welcome to your personal finance tracker!</p>
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