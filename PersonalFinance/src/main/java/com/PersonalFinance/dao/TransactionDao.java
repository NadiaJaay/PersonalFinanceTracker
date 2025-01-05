package com.PersonalFinance.dao;

import com.PersonalFinance.beans.Transaction;
import com.PersonalFinance.libs.UUIDGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDao {

    // Add a new transaction
	// Add a new transaction
	public static String addTransaction(String userId, String accountId, String type, String category,
	                                    double amount, Timestamp transactionDate, String description) {
	    String transactionId = UUIDGenerator.generateUUID();
	    try (Connection connection = DBConnection.getDBInstance()) {
	        connection.setAutoCommit(false); // Begin transaction

	        // Insert the transaction
	        String insertSql = "INSERT INTO transactions (transaction_id, user_id, account_id, type, category, amount, transaction_date, description) " +
	                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement insertStmt = connection.prepareStatement(insertSql);
	        insertStmt.setString(1, transactionId);
	        insertStmt.setString(2, userId);
	        insertStmt.setString(3, accountId);
	        insertStmt.setString(4, type);
	        insertStmt.setString(5, category);
	        insertStmt.setDouble(6, amount);
	        insertStmt.setTimestamp(7, transactionDate);
	        insertStmt.setString(8, description);

	        int rowsInserted = insertStmt.executeUpdate();
	        if (rowsInserted <= 0) {
	            throw new SQLException("Failed to insert transaction");
	        }

	        // Update the account balance
	        String updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
	        if ("Expense".equalsIgnoreCase(type)) {
	            updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
	        }
	        PreparedStatement updateStmt = connection.prepareStatement(updateSql);
	        updateStmt.setDouble(1, amount);
	        updateStmt.setString(2, accountId);

	        int rowsUpdated = updateStmt.executeUpdate();
	        if (rowsUpdated <= 0) {
	            throw new SQLException("Failed to update account balance");
	        }

	        connection.commit(); // Commit transaction
	        System.out.println("Transaction added and account balance updated successfully. Transaction ID: " + transactionId);
	        return transactionId;

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}


    // Fetch all transactions for a user
    public static List<Transaction> fetchTransactionsByUser(String userId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "SELECT * FROM transactions WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getString("transaction_id"));
                transaction.setUserId(resultSet.getString("user_id"));
                transaction.setAccountId(resultSet.getString("account_id"));
                transaction.setType(resultSet.getString("type"));
                transaction.setCategory(resultSet.getString("category"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date"));
                transaction.setDescription(resultSet.getString("description"));

                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transactions;
    }

 // Delete a transaction and revert account balance
    public static boolean deleteTransaction(String transactionId) {
        Connection connection = null;
        try {
            connection = DBConnection.getDBInstance();
            connection.setAutoCommit(false); // Begin transaction

            // Fetch transaction details
            String fetchSql = "SELECT account_id, type, amount FROM transactions WHERE transaction_id = ?";
            PreparedStatement fetchStmt = connection.prepareStatement(fetchSql);
            fetchStmt.setString(1, transactionId);
            ResultSet resultSet = fetchStmt.executeQuery();

            if (!resultSet.next()) {
                System.err.println("Transaction not found: " + transactionId);
                return false;
            }

            String accountId = resultSet.getString("account_id");
            String type = resultSet.getString("type");
            double amount = resultSet.getDouble("amount");

            // Revert account balance based on transaction type
            String updateSql;
            if ("Income".equalsIgnoreCase(type)) {
                updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            } else if ("Expense".equalsIgnoreCase(type)) {
                updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            } else {
                throw new SQLException("Unknown transaction type: " + type);
            }
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setDouble(1, amount);
            updateStmt.setString(2, accountId);

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated <= 0) {
                throw new SQLException("Failed to update account balance during transaction deletion");
            }

            // Delete the transaction
            String deleteSql = "DELETE FROM transactions WHERE transaction_id = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
            deleteStmt.setString(1, transactionId);

            int rowsDeleted = deleteStmt.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new SQLException("Failed to delete transaction: " + transactionId);
            }

            connection.commit(); // Commit transaction
            System.out.println("Transaction deleted and account balance reverted successfully. Transaction ID: " + transactionId);
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction on error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Ensure connection is closed
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
        return false;
    }
    
    public static Map<String, Double> fetchIncomeAndExpenseTotals(String userId) {
        Map<String, Double> totals = new HashMap<>();
        String sql = "SELECT type, SUM(amount) AS total FROM transactions WHERE user_id = ? GROUP BY type";

        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                totals.put(rs.getString("type"), rs.getDouble("total"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return totals;
    }
}
