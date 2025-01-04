package com.PersonalFinance.dao;

import com.PersonalFinance.beans.Account;
import com.PersonalFinance.libs.UUIDGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    // Create Account
    public static String createAccount(String userId, String accountName, String description, float balance, Timestamp createdAt) {
        String accountId = UUIDGenerator.generateUUID();

        if (createdAt == null) {
            createdAt = new Timestamp(System.currentTimeMillis());
        }

        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "INSERT INTO accounts (account_id, user_id, account_name, description, balance, created_at) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, accountId);
            stmt.setString(2, userId);
            stmt.setString(3, accountName);
            stmt.setString(4, description);
            stmt.setFloat(5, balance);
            stmt.setTimestamp(6, createdAt);
            
         // Set description to NULL if it is null or empty
            if (description == null || description.trim().isEmpty()) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, description);
            }

            stmt.setFloat(5, balance);
            stmt.setTimestamp(6, createdAt);


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Account created successfully. Account ID: " + accountId);
                return accountId;
            } else {
                System.err.println("Failed to create account.");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Delete Account
    public static boolean deleteAccount(String accountId) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "DELETE FROM accounts WHERE account_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, accountId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Account deleted successfully. Account ID: " + accountId);
                return true;
            } else {
                System.err.println("No account found with ID: " + accountId);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Edit Account
    public static boolean editAccount(String accountId, String accountName, float balance, String description) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "UPDATE accounts SET account_name = ?, balance = ?, description = ? WHERE account_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, accountName);
            stmt.setFloat(2, balance);
            stmt.setString(3, description); 
            stmt.setString(4, accountId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Account updated successfully. Account ID: " + accountId);
                return true;
            } else {
                System.err.println("No account found with ID: " + accountId);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Fetch All Accounts for a User
    public static List<Account> fetchAllAccounts(String userId) {
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "SELECT account_id, account_name, balance, description, created_at FROM accounts WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccount_id(resultSet.getString("account_id"));
                account.setUser_id(userId);
                account.setAccountName(resultSet.getString("account_name"));
                account.setDescription(resultSet.getString("description"));
                account.setBalance(resultSet.getFloat("balance"));
                account.setCreatedAt(resultSet.getTimestamp("created_at"));

                accounts.add(account);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    // Fetch Account by ID
    public static Account fetchAccountById(String accountId) {
        Account account = null;

        try (Connection connection = DBConnection.getDBInstance()) {
        	String sql = "SELECT account_id, user_id, account_name, description, balance, created_at FROM accounts WHERE account_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, accountId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setAccount_id(resultSet.getString("account_id"));
                account.setUser_id(resultSet.getString("user_id"));
                account.setAccountName(resultSet.getString("account_name"));
                account.setDescription(resultSet.getString("description"));
                account.setBalance(resultSet.getFloat("balance"));
                account.setCreatedAt(resultSet.getTimestamp("created_at"));
 
            }
            
            System.out.println("Fetched account_id: " + resultSet.getString("account_id"));
            System.out.println("Fetched user_id: " + resultSet.getString("user_id"));
            System.out.println("Fetched account_name: " + resultSet.getString("account_name"));
            System.out.println("Fetched description: " + resultSet.getString("description"));
            System.out.println("Fetched balance: " + resultSet.getFloat("balance"));
            System.out.println("Fetched created_at: " + resultSet.getTimestamp("created_at"));

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        

        return account;
    }
}
