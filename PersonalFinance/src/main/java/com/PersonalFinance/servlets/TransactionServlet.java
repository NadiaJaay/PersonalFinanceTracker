package com.PersonalFinance.servlets;

import com.PersonalFinance.beans.Account;
import com.PersonalFinance.beans.Transaction;
import com.PersonalFinance.dao.AccountDao;
import com.PersonalFinance.dao.TransactionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = (String) request.getSession().getAttribute("loggedInUserId");

        System.out.println("TransactionServlet: doGet invoked with action = " + action);

        if (userId == null) {
            System.out.println("TransactionServlet: User is not logged in. Redirecting to login.");
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("TransactionServlet: Logged-in userId = " + userId);

        // Default to "list" if action is null
        if (action == null || action.isEmpty()) {
            action = "list";
        }

        if ("list".equals(action)) {
            // Fetch all transactions for the logged-in user
            List<Transaction> transactions = TransactionDao.fetchTransactionsByUser(userId);
            System.out.println("TransactionServlet: Fetched transactions count = " + (transactions != null ? transactions.size() : 0));

            if (transactions == null || transactions.isEmpty()) {
                System.out.println("TransactionServlet: No transactions found for userId = " + userId);
            }

            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("transactions.jsp").forward(request, response);
        } else if ("create".equals(action)) {
            // Fetch accounts for creating a transaction
            List<Account> accounts = AccountDao.fetchAllAccounts(userId);
            System.out.println("TransactionServlet: Fetched accounts count = " + (accounts != null ? accounts.size() : 0));
            
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("createTransaction.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            // Delete a transaction
            String transactionId = request.getParameter("id");
            boolean success = TransactionDao.deleteTransaction(transactionId);
            System.out.println("TransactionServlet: Delete transaction success = " + success);

            response.sendRedirect("transactions?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = (String) request.getSession().getAttribute("loggedInUserId");

        if ("create".equals(action)) {
            // Create a new transaction
            String accountId = request.getParameter("accountId");
            String type = request.getParameter("type"); // Income or Expense
            String category = request.getParameter("category");
            double amount = Double.parseDouble(request.getParameter("amount"));
            Date transactionDate = Date.valueOf(request.getParameter("transactionDate"));
            String description = request.getParameter("description");

            // Call the addTransaction method
            TransactionDao.addTransaction(userId, accountId, type, category, amount, new Timestamp(transactionDate.getTime()), description);

            // Redirect to transactions list page
            response.sendRedirect("transactions?action=list");
        }
    }
}
