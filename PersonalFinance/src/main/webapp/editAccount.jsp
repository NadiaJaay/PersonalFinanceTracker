<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.PersonalFinance.beans.Account" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Account</title>
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
            <a href="goals.jsp">Goals</a>
            <a href="index.jsp">Logout</a>
        </nav>
    </div>

    <!-- Main Content -->
    <main class="dashboard-container">
        <h2 class="welcome-message">Edit Account</h2>
        <%
            Account account = (Account) request.getAttribute("account");
            if (account != null) {
        %>
            <form action="accounts" method="POST">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="accountId" value="<%= account.getAccount_id() %>">

                <label for="accountName">Account Name:</label>
                <select id="accountName" name="accountName" required>
                    <option value="Savings" <%= "Savings".equals(account.getAccountName()) ? "selected" : "" %>>Savings</option>
                    <option value="Checking" <%= "Checking".equals(account.getAccountName()) ? "selected" : "" %>>Checking</option>
                    <option value="Cash" <%= "Cash".equals(account.getAccountName()) ? "selected" : "" %>>Cash</option>
                </select><br><br>

                <label for="balance">Balance:</label>
                <input type="number" id="balance" name="balance" step="0.01" value="<%= account.getBalance() %>" required><br><br>

                <label for="description">Description:</label>
                <input type="text" id="description" name="description" maxlength="255" value="<%= account.getDescription() != null ? account.getDescription() : "" %>"><br><br>

              	<a href="accounts?action=list" class="back-button">Back</a>
				<button type="submit" class="save-button">Save Changes</button>

               
            </form>
        <%
            } else {
        %>
            <p>Account not found.</p>
            <a href="accounts?action=list" class="btn-delete">Back</a>
        <%
            }
        %>
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
