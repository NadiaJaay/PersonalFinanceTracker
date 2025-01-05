package com.PersonalFinance.servlets;

import com.PersonalFinance.dao.TransactionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("loggedInUserId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Fetch income and expense totals
        Map<String, Double> incomeExpenseData = TransactionDao.fetchIncomeAndExpenseTotals(userId);

        request.setAttribute("income", incomeExpenseData.getOrDefault("Income", 0.0));
        request.setAttribute("expense", incomeExpenseData.getOrDefault("Expense", 0.0));
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
