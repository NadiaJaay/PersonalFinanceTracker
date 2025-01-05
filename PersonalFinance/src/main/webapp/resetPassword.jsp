<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <link rel="stylesheet" href="css/password.css">
</head>
<body class="background">

    <div class="forgot-container">
        <div class="forgot-card">
            <h2 class="forgot-title">Reset Your Password</h2>
            <p class="forgot-subtitle">Enter a new password for your account</p>
            
            <!-- Success message -->
            <c:if test="${not empty success}">
                <p class="success-message">${success}</p>
            </c:if>

            <!-- Error message -->
            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>

            <form action="ResetPasswordServlet" method="post" class="forgot-form">
                <input type="hidden" name="token" value="${token}">

                <div class="form-group">
                    <label for="new_password" class="form-label">New Password:</label>
                    <input type="password" id="new_password" name="new_password" class="form-input" placeholder="Enter your new password" required>
                </div>

                <button type="submit" class="btn-forgot">Reset Password</button>
            </form>
        </div>
    </div>

</body>
</html>