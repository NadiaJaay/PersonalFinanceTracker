<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Finance | Register</title>
    <link rel="stylesheet" href="css/register-style.css">
</head>
<body class="background">
    <div class="overlay">
        <div class="register-container">
            <h2>Register for Personal Finance Tracker</h2>

            <!-- Display error message if present -->
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null && !message.isEmpty()) { %>
                <p class="error-message" style="color: red;"><%= message %></p>
            <% } %>

            <form class="register-form" method="POST" action="register">
            
                <label for="email">Email:</label>
                <input id="email" type="email" name="email" placeholder="email@example.com" pattern="\S+@\S+\.\S+" required value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" />

                <label for="username">Username:</label>
                <input id="username" type="text" name="username" placeholder="Username" required value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" />

                <label for="password">Password:</label>
                <input id="password" type="password" name="password" placeholder="Enter your password" required />

                <label for="first_name">First Name:</label>
                <input id="first_name" type="text" name="first_name" placeholder="First Name" required value="<%= request.getParameter("first_name") != null ? request.getParameter("first_name") : "" %>" />

                <label for="last_name">Last Name:</label>
                <input id="last_name" type="text" name="last_name" placeholder="Last Name" required value="<%= request.getParameter("last_name") != null ? request.getParameter("last_name") : "" %>" />
    
                <input id="user_role" type="hidden" name="user_role" value="member" />
                <button type="submit" class="btn-register">Register</button>
            </form>

				<p class="login-prompt"><a href="index.jsp">Already have an account? Login Now</a></p>
        </div>
    </div>

    <footer class="footer">
        <p>Created by Nadia Jujooste</p>
        <p>&copy; 2025 Personal Finance Tracker Application</p>
    </footer>
</body>
</html>