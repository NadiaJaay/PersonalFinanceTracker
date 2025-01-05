package com.PersonalFinance.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.PersonalFinance.dao.PasswordResetDao;
import com.PersonalFinance.dao.UserDao;

import java.io.IOException;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            request.setAttribute("message", "Invalid password reset token.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        try {
            boolean isValidToken = PasswordResetDao.validateToken(token);

            if (isValidToken) {
                request.setAttribute("token", token);
                request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Invalid or expired password reset link.");
                request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("message", "An error occurred. Please try again.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("new_password");

        if (token == null || token.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            request.setAttribute("message", "All fields are required.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        try {
            String userId = PasswordResetDao.getUserIdByToken(token);

            if (userId != null) {
                UserDao.updatePassword(userId, newPassword);

                PasswordResetDao.markTokenAsCompleted(token);

                request.setAttribute("message", "Password has been reset successfully! You can now log in.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Invalid or expired password reset token.");
                request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("message", "An error occurred while resetting your password. Please try again.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }
}