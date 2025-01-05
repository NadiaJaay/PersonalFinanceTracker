<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.PersonalFinance.beans.Transaction" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Finance | Transactions</title>
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
        <h2 class="welcome-message">Your Transactions</h2>

		<a href="transactions?action=create" class="add-new-account">Add a Transaction</a>

        <div class="card-container">
            <%
                List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");

                if (transactions != null && !transactions.isEmpty()) {
                    for (Transaction transaction : transactions) {
            %>
                        <div class="book-card">
                            <h3>Transaction: <%= transaction.getType() %></h3>
                            <p><strong>Account:</strong> <%= transaction.getAccountId() %></p>
                            <p><strong>Category:</strong> <%= transaction.getCategory() %></p>
                            <p><strong>Amount:</strong> $<%= String.format("%,.2f", transaction.getAmount()) %></p>
                            <p><strong>Date:</strong> <%= transaction.getTransactionDate() %></p>
                            <p><strong>Description:</strong> <%= (transaction.getDescription() != null ? transaction.getDescription() : "No description provided") %></p>
                            <div class="card-actions">
                                <a href="transactions?action=delete&id=<%= transaction.getTransactionId() %>" class="btn-delete">Delete</a>
                            </div>
                        </div>
            <%
                    }
                } else {
            %>
                <p>No transactions found.</p>
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
