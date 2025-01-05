<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="css/password.css">
</head>
<body class="background">

    <div class="forgot-container">
        <div class="forgot-card">
            <h2 class="forgot-title">Forgot Password</h2>
            <p class="forgot-subtitle">Enter your email address to reset your password</p>
            
            <!-- Success message -->
            <c:if test="${not empty success}">
                <p class="success-message">${success}</p>
            </c:if>

            <!-- Error message -->
            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>

            <form action="ForgotPasswordServlet" method="post" class="forgot-form">
                <div class="form-group">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" id="email" name="email" class="form-input" placeholder="Enter your email" required>
                </div>
                <button type="submit" class="btn-forgot">Recover Password</button>
            </form>
        </div>
    </div>

</body>
</html>