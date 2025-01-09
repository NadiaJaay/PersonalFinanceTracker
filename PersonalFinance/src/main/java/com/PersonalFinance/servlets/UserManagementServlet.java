package com.PersonalFinance.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.PersonalFinance.beans.User;
import com.PersonalFinance.dao.RoleDao;
import com.PersonalFinance.dao.UserDao;
import com.PersonalFinance.dao.UserRoleDao;

@WebServlet("/admin")
public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserManagementServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch all users
            request.setAttribute("users", UserDao.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving users.");
        }

        request.getRequestDispatcher("userManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equalsIgnoreCase(action)) {
                // Add a new user
                handleAddUser(request);
            } else if ("delete".equalsIgnoreCase(action)) {
                // Delete a user
                String userId = request.getParameter("userId");
                UserDao.deleteUser(userId);
            } else if ("unverify".equalsIgnoreCase(action)) {
                // Unverify a user
                String userId = request.getParameter("userId");
                UserDao.updateVerificationStatus(userId, false);
            } else if ("verify".equalsIgnoreCase(action)) {
                // Verify a user
                String userId = request.getParameter("userId");
                UserDao.updateVerificationStatus(userId, true);
            } else if ("edit".equalsIgnoreCase(action)) {
                // Edit a user: redirect to editUser.jsp
                String userId = request.getParameter("userId");
                User user = UserDao.getUserById(userId); 

                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("editUser.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("error", "User not found.");
                }
            } else if ("editSave".equalsIgnoreCase(action)) {
                // Save the edited user details
                handleEditSaveUser(request);
            }

            // Redirect back to the user management page
            response.sendRedirect("admin");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing the request.");
            request.getRequestDispatcher("userManagement.jsp").forward(request, response);
        }
    }

    private void handleAddUser(HttpServletRequest request) throws Exception {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String roleName = request.getParameter("role"); 

        // Find the role ID based on the selected role name
        String roleId = RoleDao.findRoleIdByName(roleName);
        if (roleId == null) {
            throw new RuntimeException("Selected role not found");
        }

        // Create the user
        String userId = UserDao.createUser(email, username, password, firstName, lastName, roleId);
        if (userId == null) {
            throw new RuntimeException("Failed to create the user");
        }

        // Assign the role to the user
        UserRoleDao.assign_role(userId, roleId);
    }

    private void handleEditSaveUser(HttpServletRequest request) throws Exception {
        String userId = request.getParameter("userId");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");

        // Call the DAO method to update the user
        boolean success = UserDao.updateUser(userId, username, email, firstName, lastName);

        if (!success) {
            throw new RuntimeException("Failed to update the user");
        }
    }
}
