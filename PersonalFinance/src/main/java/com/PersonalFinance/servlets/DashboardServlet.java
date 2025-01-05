package com.PersonalFinance.servlets;

import com.PersonalFinance.dao.TransactionDao;
import com.PersonalFinance.dao.AccountDao;
import com.PersonalFinance.dao.GoalDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("loggedInUserId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Map<String, Double> spendingByCategory = TransactionDao.fetchSpendingByCategory(userId);
        String spendingByCategoryJson = new Gson().toJson(spendingByCategory);
        request.setAttribute("spendingByCategory", spendingByCategoryJson);

        // Fetch income and expense totals
        Map<String, Double> incomeExpenseData = TransactionDao.fetchIncomeAndExpenseTotals(userId);

        // Fetch total balances by account type (e.g., Checkings, Savings, Cash)
        Map<String, Double> accountBalances = AccountDao.fetchTotalBalancesByAccountType(userId);
        String accountBalancesJson = new Gson().toJson(accountBalances);
        System.out.println("Account Balances: " + accountBalancesJson);

        // Fetch goal completion percentage
        float goalCompletionPercentage = GoalDao.fetchGoalCompletionPercentage(userId);
        request.setAttribute("goalCompletionPercentage", goalCompletionPercentage);
        
        // Pass data to the JSP
        request.setAttribute("income", incomeExpenseData.getOrDefault("Income", 0.0));
        request.setAttribute("expense", incomeExpenseData.getOrDefault("Expense", 0.0));
        request.setAttribute("accountBalances", accountBalances);

        // Forward to the dashboard JSP
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
