package com.PersonalFinance.servlets;

import com.PersonalFinance.beans.Account;
import com.PersonalFinance.dao.AccountDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = (String) request.getSession().getAttribute("loggedInUserId");

        // Debugging: Check if the session contains the loggedInUserId
        System.out.println("Logged in user ID Account serv: " + userId);

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if ("list".equals(action) || action == null) {
            // Fetch all accounts for the logged-in user
            List<Account> accounts = AccountDao.fetchAllAccounts(userId);

            // Debugging: Log the number of accounts fetched
            if (accounts != null) {
                System.out.println("Accounts fetched for user: " + userId + ", Total accounts: " + accounts.size());
            } else {
                System.out.println("No accounts found for user: " + userId);
            }

            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("accounts.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            // Fetch account for editing
            String accountId = request.getParameter("id");
            Account account = AccountDao.fetchAccountById(accountId);

            // Debugging: Log account details being edited
            if (account != null) {
            	System.out.println("Account ID: " + account.getAccount_id());
            	System.out.println("Balance: " + account.getBalance());

                System.out.println("Editing account: " + account.getAccountName() + ", ID: " + accountId);
            } else {
                System.out.println("No account found with ID: " + accountId);
            }

            request.setAttribute("account", account);
            request.getRequestDispatcher("editAccount.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            // Delete an account
            String accountId = request.getParameter("id");
            boolean success = AccountDao.deleteAccount(accountId);

            // Debugging: Log success or failure of deletion
            if (success) {
                System.out.println("Account deleted successfully. ID: " + accountId);
                response.sendRedirect("accounts?action=list");
            } else {
                System.out.println("Failed to delete account with ID: " + accountId);
                request.setAttribute("error", "Failed to delete account.");
                request.getRequestDispatcher("accounts.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = (String) request.getSession().getAttribute("loggedInUserId");

        // Debugging: Log action type
        System.out.println("Action: " + action);

        if ("create".equals(action)) {
            // Create a new account
            String accountName = request.getParameter("accountName");
            String description = request.getParameter("description");
            float balance = Float.parseFloat(request.getParameter("balance"));
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            
         // Ensure description is optional
            if (description == null || description.trim().isEmpty()) {
                description = null; // Set to null if not provided
            }

            String accountId = AccountDao.createAccount(userId, accountName, description, balance, createdAt);

            // Debugging: Log success or failure of account creation
            if (accountId != null) {
                System.out.println("Account created successfully. ID: " + accountId);
                response.sendRedirect("accounts?action=list");
            } else {
                System.out.println("Failed to create account for user: " + userId);
                request.setAttribute("error", "Failed to create account.");
                request.getRequestDispatcher("createAccount.jsp").forward(request, response);
            }
        } else if ("edit".equals(action)) {
            // Update an existing account
            String accountId = request.getParameter("accountId");
            String accountName = request.getParameter("accountName");
            String description = request.getParameter("description");
            float balance = Float.parseFloat(request.getParameter("balance"));

            boolean success = AccountDao.editAccount(accountId, accountName, balance, description);

            // Debugging: Log success or failure of account update
            if (success) {
                System.out.println("Account updated successfully. ID: " + accountId);
                response.sendRedirect("accounts?action=list");
            } else {
                System.out.println("Failed to update account. ID: " + accountId);
                request.setAttribute("error", "Failed to update account.");
                request.getRequestDispatcher("editAccount.jsp").forward(request, response);
            }
        }
    }
}
