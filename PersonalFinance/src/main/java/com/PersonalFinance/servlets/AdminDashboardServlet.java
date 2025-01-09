package com.PersonalFinance.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.PersonalFinance.dao.UserDao;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminDashboardServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Dynamically fetch the current month and year
            LocalDate now = LocalDate.now();
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();

            // Fetch data from the database
            int totalUsersForMonth = UserDao.getTotalUsersForMonth(currentMonth, currentYear);
            int totalUsersAllTime = UserDao.getTotalUsersAllTime();

            // Set attributes for the JSP
            request.setAttribute("totalUsersThisMonth", totalUsersForMonth);
            request.setAttribute("totalUsersAllTime", totalUsersAllTime);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching data.");
        }

        // Forward to the JSP
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }


}
