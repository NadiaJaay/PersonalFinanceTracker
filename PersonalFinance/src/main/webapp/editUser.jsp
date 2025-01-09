<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

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
        <h2 class="welcome-message">Edit User</h2>

        <form action="admin" method="post" class="form-container">
            <input type="hidden" name="action" value="editSave">
            <input type="hidden" name="userId" value="${user.user_id}">

            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${user.username}" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" required>
            </div>

            <div class="form-group">
                <label for="first_name">First Name:</label>
                <input type="text" id="first_name" name="first_name" value="${user.first_name}" required>
            </div>

            <div class="form-group">
                <label for="last_name">Last Name:</label>
                <input type="text" id="last_name" name="last_name" value="${user.last_name}" required>
            </div>

            <div class="form-actions">
                <button type="submit" class="save-button">Save Changes</button>
                <a href="admin" class="back-button">Cancel</a>
            </div>
        </form>
    </main>

</body>
</html>
