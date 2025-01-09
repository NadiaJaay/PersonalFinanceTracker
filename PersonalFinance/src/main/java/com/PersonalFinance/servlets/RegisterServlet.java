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
import com.PersonalFinance.libs.TokenGenerator;
import com.PersonalFinance.services.JavaEmailService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // User input
        String email = request.getParameter("email").toLowerCase();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");

        try {

            // Retrieve the member role ID
            String member_role_id = RoleDao.findRoleIdByName("member");

            // Create the user in the database
            String user_id = UserDao.createUser(email, username, password, first_name, last_name, member_role_id);
            if (user_id == null) {
                throw new Exception("Failed to create user.");
            }

            // Generate and save the verification token
            String token = TokenGenerator.generatedToken();
            UserDao.saveVerificationToken(user_id, token);

            // Send verification email
            JavaEmailService emailService = new JavaEmailService();
            String verificationLink = "http://localhost:8080/PersonalFinance/verifyServlet?verification_code=" + token;
            emailService.sendVerificationEmail(email, verificationLink);

            // Assign the member role to the user
            UserRoleDao.assign_role(user_id, member_role_id);

            request.setAttribute("message", "User registered successfully. Please check your email for verification.");
            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
