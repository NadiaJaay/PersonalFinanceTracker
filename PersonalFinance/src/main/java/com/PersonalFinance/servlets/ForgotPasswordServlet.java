package com.PersonalFinance.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.PersonalFinance.dao.UserDao;
import com.PersonalFinance.dao.PasswordResetDao;
import com.PersonalFinance.services.JavaEmailService;
import com.PersonalFinance.libs.TokenGenerator;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try {
            if (!UserDao.emailExists(email)) {
                request.setAttribute("message", "Email is not associated with any account.");
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
                return;
            }

            String userId = UserDao.getUserIdByEmail(email);

            String token = TokenGenerator.generatedToken();

            PasswordResetDao.saveResetToken(userId, token);

            String resetLink = "http://localhost:8080/PersonalFinance/ResetPasswordServlet?token=" + token;

            JavaEmailService emailService = new JavaEmailService();
            emailService.sendPasswordResetEmail(email, resetLink);

            // Success message
            request.setAttribute("success", "Check your email for the password recovery link.");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);


        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
}