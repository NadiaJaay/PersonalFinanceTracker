<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Finance | Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="css/dashboard.css">

</head>
<body class="background">

    <!-- Sidebar Toggle Button -->
    <button class="sidebar-toggle" onclick="toggleSidebar()">☰</button>

    <!-- Sidebar -->
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
        <h2 class="welcome-message">Your Dashboard</h2>
<!-- Doughnut Chart -->
    <div style="width: 50%; margin: auto;">
        <canvas id="incomeExpenseChart"></canvas>
    </div>
	</main>
		<script>
		    function toggleSidebar() {
		        const sidebar = document.getElementById('sidebar');
		        sidebar.classList.toggle('active');
		    }
		</script>
	
		<script>
		    // Get data from the server-side attributes
		    const income = <%= request.getAttribute("income") != null ? request.getAttribute("income") : 0.0 %>;
		    const expense = <%= request.getAttribute("expense") != null ? request.getAttribute("expense") : 0.0 %>;
		
		    // Render the Doughnut Chart
		    const ctx = document.getElementById('incomeExpenseChart').getContext('2d');
		    new Chart(ctx, {
		        type: 'doughnut',
		        data: {
		            labels: ['Income', 'Expense'],
		            datasets: [{
		                label: 'Income vs Expense',
		                data: [income, expense],
		                backgroundColor: ['#4CAF50', '#F44336'],
		                hoverOffset: 4
		            }]
		        },
		        options: {
		            responsive: true,
		            plugins: {
		                legend: {
		                    position: 'top',
		                },
		            }
		        }
		    });
		</script>
</body>
</html>