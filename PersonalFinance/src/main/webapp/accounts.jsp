<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.PersonalFinance.beans.Account" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Finance | Accounts</title>
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
        <h2 class="welcome-message">Your Accounts</h2>
        
        <a href="createAccount.jsp" class="add-new-account">Add New Account</a>

        <!-- Card Container -->
        <div class="card-container">
        
            <%
                List<Account> accounts = (List<Account>) request.getAttribute("accounts");

                // Debugging: Log if the accounts attribute is null
                if (accounts == null) {
                    System.out.println("The 'accounts' attribute is null.");
                } else {
                    System.out.println("Number of accounts retrieved: " + accounts.size());
                }

                if (accounts != null && !accounts.isEmpty()) {
                    for (Account account : accounts) {
            %>
                        <div class="book-card">
                            <h3><%= account.getAccountName() %> Account</h3>
							<p><strong>Balance:</strong> $<%= String.format("%,.2f", account.getBalance()) %></p>
							<p><strong>Description:</strong> <%= (account.getDescription() != null ? account.getDescription() : "No description provided") %></p>
                            <p><strong>Created On:</strong> <%= account.getCreatedAt() %></p>
                            <div class="card-actions">
                                <a href="accounts?action=edit&id=<%= account.getAccount_id() %>" class="btn-edit">Edit</a>
                                <a href="accounts?action=delete&id=<%= account.getAccount_id() %>" class="btn-delete">Delete</a>
                            </div>
                        </div>
            <%
                    }
                } else {
            %>
                <!-- No Accounts Found Message -->
                <div class="book-card no-accounts-card">
                    <p>No accounts found.</p>
                </div>
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
