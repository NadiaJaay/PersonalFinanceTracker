package com.PersonalFinance.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.PersonalFinance.dao.UserDao;

import java.io.IOException;

@WebServlet("/verifyServlet")
public class VerifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String verification_code = request.getParameter("verification_code");

        if (verification_code == null || verification_code.isEmpty()) {
            request.setAttribute("message", "Invalid verification token.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        try {
            // Verify the token and activate the user
            boolean is_verified = UserDao.verifyUserByToken(verification_code);
            if (is_verified) {
                request.setAttribute("message", "Email verified successfully! You can now log in.");

            } else {
                request.setAttribute("message", "Invalid or expired verification link.");
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "An error occurred during verification. Please try again.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
