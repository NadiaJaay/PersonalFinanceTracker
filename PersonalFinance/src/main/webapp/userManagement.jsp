<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management</title>
    <link rel="stylesheet" href="css/dashboard.css"> <!-- Ensure this path is correct -->
</head>
<body class="background">

    <!-- Sidebar Toggle Button -->
    <button class="sidebar-toggle" onclick="toggleSidebar()">&#9776;</button>

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
        <h2 class="welcome-message">User Management</h2>

        <!-- Filter and Add User Section -->
        <div class="filter-container">
            <a href="addUser.jsp" class="add-new-account">Add New User</a>
        </div>

        <!-- User Table -->
        <table border="1" class="user-table">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Verified</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.first_name}</td>
                        <td>${user.last_name}</td>
                        <td>${user.is_verified ? 'Yes' : 'No'}</td>
                        <td>
                            <form method="post" action="admin" style="display: inline;">
                                <input type="hidden" name="userId" value="${user.user_id}">
                                <button type="submit" name="action" value="edit" class="btn-edit">Edit</button>
                            </form>
                            <form method="post" action="admin" style="display: inline;">
                                <input type="hidden" name="userId" value="${user.user_id}">
                                <button type="submit" name="action" value="delete" class="btn-delete" onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
                            </form>
                            <form method="post" action="admin" style="display: inline;">
                                <input type="hidden" name="userId" value="${user.user_id}">
                                <button type="submit" name="action" value="${user.is_verified ? 'unverify' : 'verify'}" class="btn-${user.is_verified ? 'unverify' : 'verify'}">
                                    ${user.is_verified ? 'Unverify' : 'Verify'}
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
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
    </script>
</body>
</html>
