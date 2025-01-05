<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
        
        <!-- Charts Container for Income vs Expense and Account Balances -->
        <div class="charts-container">
            <div class="chart-box">
                <h3 style="text-align: center;">Income vs Expense</h3>
                <canvas id="incomeExpenseChart"></canvas>
            </div>

            <div class="chart-box">
                <h3 style="text-align: center;">Account Balances</h3>
                <canvas id="accountBalancesChart"></canvas>
            </div>
        </div>

        <!-- Charts Container for Spending by Category and Goal Completion -->
        <div class="charts-container">
            <div class="chart-box">
                <h3 style="text-align: center;">Spending by Category</h3>
                <canvas id="spendingCategoryChart"></canvas>
            </div>

            <div class="chart-box">
    <h3 style="text-align: center;">Goal Completion</h3>
    <div class="goal-completion-box">
        <div class="goal-bar-container">
            <div class="goal-bar" style="width: <%= request.getAttribute("goalCompletionPercentage") %>%;">
            </div>
        </div>
        <p style="font-weight: bold; font-size: 1rem; text-align: center; margin: 0;">
            <%= request.getAttribute("goalCompletionPercentage") != null 
                ? request.getAttribute("goalCompletionPercentage") + "%" 
                : "0%" %>
        </p>
    </div>
</div>

    </main>

    <!-- Footer -->
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

        // Data for Income vs Expense
        const income = <%= request.getAttribute("income") != null ? request.getAttribute("income") : 0.0 %>;
        const expense = <%= request.getAttribute("expense") != null ? request.getAttribute("expense") : 0.0 %>;

        // Render Doughnut Chart for Income vs Expense
        new Chart(document.getElementById('incomeExpenseChart').getContext('2d'), {
            type: 'doughnut',
            data: {
                labels: ['Income', 'Expense'],
                datasets: [{
                    data: [income, expense],
                    backgroundColor: ['#4CAF50', '#F44336']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' }
                }
            }
        });

        // Data for Account Balances
        const accountBalances = <%= new com.google.gson.Gson().toJson(request.getAttribute("accountBalances")) %>;
        const accountLabels = Object.keys(accountBalances);
        const accountData = Object.values(accountBalances);

        // Render Pie Chart for Account Balances
        new Chart(document.getElementById('accountBalancesChart').getContext('2d'), {
            type: 'pie',
            data: {
                labels: accountLabels,
                datasets: [{
                    data: accountData,
                    backgroundColor: ['#4CAF50', '#2196F3', '#FFC107', '#FF9800']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' }
                }
            }
        });

        // Data for Spending by Category
        const spendingData = <%= request.getAttribute("spendingByCategory") %>;
        const categoryLabels = Object.keys(spendingData);
        const categoryAmounts = Object.values(spendingData);

        // Render Horizontal Bar Chart for Spending by Category
        new Chart(document.getElementById('spendingCategoryChart').getContext('2d'), {
            type: 'bar',
            data: {
                labels: categoryLabels,
                datasets: [{
                    label: 'Amount Spent ($)',
                    data: categoryAmounts,
                    backgroundColor: [
                        '#4CAF50', '#2196F3', '#FFC107', '#FF9800',
                        '#E91E63', '#9C27B0', '#3F51B5', '#00BCD4',
                        '#8BC34A', '#CDDC39', '#FF5722'
                    ]
                }]
            },
            options: {
                indexAxis: 'y', // Horizontal bar chart
                responsive: true,
                plugins: {
                    legend: { display: true, position: 'top' },       
                },
                scales: {
                    x: {
                        beginAtZero: true,
                        title: { display: true, text: 'Amount Spent ($)' }
                    },
                    y: {
                        title: { display: true, text: 'Categories' }
                    }
                }
            }
        });
    </script>
</body>
</html>
