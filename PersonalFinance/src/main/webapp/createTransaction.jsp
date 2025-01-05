<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.PersonalFinance.beans.Account" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Transaction</title>
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
        <h2 class="welcome-message">Create a New Transaction</h2>

        <form action="transactions" method="POST" class="form-container">
            <input type="hidden" name="action" value="create">

            <!-- Account Dropdown -->
            <label for="accountId">Account:</label>
            <select id="accountId" name="accountId" required>
                <%
                    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
                    if (accounts != null && !accounts.isEmpty()) {
                        for (Account account : accounts) {
                %>
                            <option value="<%= account.getAccount_id() %>"><%= account.getAccountName() %> ($<%= String.format("%.2f", account.getBalance()) %>)</option>
                <%
                        }
                    } else {
                %>
                    <option value="">No accounts available</option>
                <%
                    }
                %>
            </select><br><br>

            <!-- Transaction Type -->
            <label for="type">Type:</label>
            <select id="type" name="type" required>
                <option value="Income">Income</option>
                <option value="Expense">Expense</option>
            </select><br><br>

            <!-- Category -->
            <label for="category">Category:</label>
			<select id="category" name="category" required>
			    <option value="" disabled selected>Select a category</option>
			    <option value="Groceries">Groceries</option>
			    <option value="Utilities">Utilities</option>
			    <option value="Transportation">Transportation</option>
			    <option value="Rent">Rent</option>
			    <option value="Entertainment">Entertainment</option>
			    <option value="Dining Out">Dining Out</option>
			    <option value="Savings">Savings</option>
			    <option value="Investment">Investment</option>
			    <option value="Health">Health</option>
			    <option value="Insurance">Insurance</option>
			    <option value="Other">Other</option>
			</select><br><br>

            <!-- Amount -->
            <label for="amount">Amount:</label>
            <input type="number" id="amount" name="amount" step="0.01" required><br><br>

            <!-- Transaction Date -->
            <label for="transactionDate">Transaction Date:</label>
            <input type="date" id="transactionDate" name="transactionDate" required><br><br>

            <!-- Description -->
            <label for="description">Description (Optional):</label>
            <input type="text" id="description" name="description" maxlength="255"><br><br>

            <!-- Buttons -->
            <a href="transactions?action=list" class="back-button">Back</a>
            <button type="submit" class="save-button">Create Transaction</button>
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
