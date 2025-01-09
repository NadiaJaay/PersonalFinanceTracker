<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Finance | Admin Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <!-- Sidebar Toggle Button -->
    <button class="sidebar-toggle" onclick="toggleSidebar()">☰</button>

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <h1>PFT</h1>
        <nav>
            <a href="adminDashboard">Dashboard</a>
			<a href="admin">Users</a>
            <a href="index.jsp">Logout</a>
        </nav>
    </div>

    <!-- Main Content -->
    <main class="dashboard-container">
        <h2 class="welcome-message">Admin Dashboard</h2>
        
        <!-- User Statistics -->
        <div class="stats-container">
            <div class="stat-box">
                <h3>Total Users (This Month)</h3>
                <p><%= request.getAttribute("totalUsersThisMonth") %></p>
            </div>
            <div class="stat-box">
                <h3>Total Users (All Time)</h3>
                <p><%= request.getAttribute("totalUsersAllTime") %></p>
            </div>
        </div>
    </main>

    <footer class="footer">
        <p>&copy; 2025 Personal Finance Tracker</p>
        <p>Created by Nadia Jujooste</p>
    </footer>
    
    <script>
        // Sidebar Toggle Function
        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            sidebar.classList.toggle('active');
        }
    </script>
</body>
</html>
