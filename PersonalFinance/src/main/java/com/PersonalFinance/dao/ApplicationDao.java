package com.PersonalFinance.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationDao {
	
	private static ApplicationDao dao;
	public static final String DB_NAME = "finance";
	public static final String USERS_TABLE = "users";
	public static final String ACCOUNTS_TABLE = "accounts";
	public static final String TRANSACTIONS_TABLE = "transactions";
	public static final String SAVINGS_TABLE = "savings";
	public static final String ROLES_TABLE = "roles";
    public static final String USER_ROLE_TABLE = "user_role";
	public static final String VERIFICATION_TABLE = "verification";
    public static final String PASSWORD_RESET_TABLE = "password_reset";

	
    private ApplicationDao() {}
    
    public static synchronized ApplicationDao getDao() {
    	
        if (dao == null) dao = new ApplicationDao();
        return dao;
    }

    public static void createDatabase() {
    	
        try (
                Connection conn = DBConnection.getDBInstance();
                ResultSet resultSet = conn.getMetaData().getCatalogs();
                Statement stmt = conn.createStatement();
            ) {
            if (!dbExists(DB_NAME, resultSet)) {
                System.out.print("Creating DB...");
                String sql = "CREATE DATABASE IF NOT EXISTS "+ DB_NAME +" DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci";
                stmt.executeUpdate(sql);
                System.out.println("Created DB");
            }
            DBUtil.setConnStr();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createUsersTable() {
    	try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
                if (!tableExists(conn, USERS_TABLE)) {
                    System.out.print("Creating User Table...");
                    String sql = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " ("
                            + "user_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                            + "email VARCHAR(255) NOT NULL UNIQUE, "
                            + "username VARCHAR(30) NOT NULL UNIQUE, "
                            + "password VARCHAR(128) NOT NULL, "
                            + "first_name VARCHAR(35) NOT NULL, "
                            + "last_name VARCHAR(35) NOT NULL, "
                            + "is_verified TINYINT NOT NULL DEFAULT 0, "
                            + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, "
                            + "role_id VARCHAR(36), "
                            + "FOREIGN KEY (role_id) REFERENCES " + ROLES_TABLE + "(role_id) ON DELETE CASCADE"
                            + ")";
                            
                    stmt.executeUpdate(sql);
                    //insertDefaultUser(conn); 
                    System.out.println("Created User Table");
                }
            } catch (SQLException e) {
                DBUtil.processException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    public void createAccountsTable() {
    	try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
                if (!tableExists(conn, ACCOUNTS_TABLE)) {
                    System.out.print("Creating Account Table...");
                    String sql = "CREATE TABLE IF NOT EXISTS " + ACCOUNTS_TABLE + " ("
                            + "account_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                            + "user_id VARCHAR(36), " 
                            + "account_name VARCHAR(35) NOT NULL, "
                            + "description VARCHAR (255), "
                            + "balance DECIMAL(10,2) NOT NULL, "
                            + "created_at DATETIME NOT NULL, "
                            + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE SET NULL"
                            + ")";
                    stmt.executeUpdate(sql);
                    System.out.println("Created Account Table");
                }
            } catch (SQLException e) {
                DBUtil.processException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    

	public void createTransactionTable() {
    	try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
                if (!tableExists(conn, TRANSACTIONS_TABLE)) {
                    System.out.print("Creating Transaction Table...");
                    String sql = "CREATE TABLE IF NOT EXISTS " + TRANSACTIONS_TABLE + " ("
                            + "transaction_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                            + "user_id VARCHAR(36), " 
                            + "account_id VARCHAR(36), " 
                            + "type VARCHAR(20) NOT NULL, "
                            + "category VARCHAR(35) NOT NULL, "
                            + "amount DECIMAL(10,2) NOT NULL, "
                            + "transaction_date DATETIME NOT NULL, "
                            + "description TEXT, "
                            + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE SET NULL, "
                            + "FOREIGN KEY (account_id) REFERENCES " + ACCOUNTS_TABLE + "(account_id) ON DELETE SET NULL"
                            + ")";
                            
                    stmt.executeUpdate(sql);
                    System.out.println("Created User Table");
                }
            } catch (SQLException e) {
                DBUtil.processException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    public void createSavingsTable() {
    	try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
                if (!tableExists(conn, SAVINGS_TABLE)) {
                    System.out.print("Creating Goals Table...");
                    String sql = "CREATE TABLE IF NOT EXISTS " + SAVINGS_TABLE + " ("
                            + "goal_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                            + "user_id CHAR(36), " 
                            + "goal_name VARCHAR(35) NOT NULL, "
                            + "target_amount DECIMAL(10,2) NOT NULL, "
                            + "current_amount DECIMAL(10,2) NOT NULL, "
                            + "description TEXT, " 
                            + "is_completed BOOLEAN DEFAULT FALSE, " 
                            + "end_date DATETIME NOT NULL, "
                            + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE SET NULL"
                            + ")";
                            
                    stmt.executeUpdate(sql);
                    System.out.println("Created Goals Table");
                }
            } catch (SQLException e) {
                DBUtil.processException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    public void createRolesTable() {
    	try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
                if (!tableExists(conn, ROLES_TABLE)) {
                    System.out.print("Creating Role Table...");
                    String sql = "CREATE TABLE IF NOT EXISTS " + ROLES_TABLE + " ("
                            + "role_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                            + "role_name VARCHAR(25) NOT NULL UNIQUE, "
                            + "description VARCHAR(255) DEFAULT NULL"
                            + ")";
                    stmt.executeUpdate(sql);
                    insertDefaultRoles(conn); // insert default roles
                    System.out.println("Created Role Table");
                }
            } catch (SQLException e) {
                DBUtil.processException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    public void createUserRoleTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, USER_ROLE_TABLE)) {
                System.out.print("Creating UserRole Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + USER_ROLE_TABLE + " ("
                        + "user_role_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                		+ "user_id VARCHAR(36) NOT NULL, "
                        + "role_id VARCHAR(36) NOT NULL, "
                		+ "assigned_date DATETIME NOT NULL, "
                        + "status VARCHAR(15) NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE CASCADE, "
                        + "FOREIGN KEY (role_id) REFERENCES " + ROLES_TABLE + "(role_id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(sql);
                insertDefaultUserRole(conn); // insert default userRoles for Admin and Reg Users
                System.out.println("Created UserRole Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createVerificationTable() {
    	try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
                if (!tableExists(conn, VERIFICATION_TABLE)) {
                    System.out.print("Creating Verification Table...");
                    String sql = "CREATE TABLE IF NOT EXISTS " + VERIFICATION_TABLE + " ("
                            + "verification_id VARCHAR(36) NOT NULL DEFAULT (UUID()) PRIMARY KEY, "
                            + "user_id VARCHAR(36) NOT NULL, "
                            + "verification_type VARCHAR(20) NOT NULL, "
                            + "verification_code VARCHAR(36) NOT NULL, "
                            + "created_at DATETIME NOT NULL, "
                            + "status VARCHAR(15) NOT NULL, "
                            + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE CASCADE"
                            + ")";
                    stmt.executeUpdate(sql);
                    System.out.println("Created Verification Table");
                }
            } catch (SQLException e) {
                DBUtil.processException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    public void createPasswordResetTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, "password_reset")) {
                System.out.print("Creating Password Reset Table...");
                String sql = "CREATE TABLE IF NOT EXISTS password_reset ("
                        + "reset_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "user_id VARCHAR(36) NOT NULL, "
                        + "token VARCHAR(64) NOT NULL UNIQUE, "
                        + "created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                        + "status ENUM('pending', 'completed') NOT NULL DEFAULT 'pending', "
                        + "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE)";
                stmt.executeUpdate(sql);
                System.out.println("Created Password Reset Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Default Admin and member data 
	/*
	 * private void insertDefaultUser(Connection conn) throws SQLException { String
	 * checkUserSql = "SELECT email FROM " + USERS_TABLE + " WHERE email = ?";
	 * String checkRoleSql = "SELECT role_id FROM " + ROLES_TABLE +
	 * " WHERE role_id = ?"; String insertUserSql = "INSERT INTO " + USERS_TABLE +
	 * " (user_id, email, username, password, first_name, last_name, is_verified, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
	 * ;
	 * 
	 * try ( PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql);
	 * PreparedStatement checkRoleStmt = conn.prepareStatement(checkRoleSql);
	 * PreparedStatement insertStmt = conn.prepareStatement(insertUserSql); ) {
	 * String[][] defaultUsers = { {"5ac66fc0-b37d-11ef-bf20-325096b39f47",
	 * "admin@email.com", "admin", "password", "System", "Admin", "true",
	 * "35ea87d3-7df2-4cf3-9e4c-d326027cbe95"},
	 * {"5ac67524-b37d-11ef-a678-325096b39f47", "member@email.com", "member",
	 * "password", "Regular", "User", "false",
	 * "e45cfed3-ed61-49ba-9215-2c44ee23bdae"} };
	 * 
	 * for (String[] user : defaultUsers) { // Check if user already exists
	 * checkUserStmt.setString(1, user[1]); try (ResultSet rs =
	 * checkUserStmt.executeQuery()) { if (rs.next()) {
	 * System.err.println("User already exists with email: " + user[1]); continue;
	 * // Skip this entry } }
	 * 
	 * // Check if role exists checkRoleStmt.setString(1, user[7]); try (ResultSet
	 * rs = checkRoleStmt.executeQuery()) { if (!rs.next()) {
	 * System.err.println("Role ID does not exist: " + user[7]); continue; // Skip
	 * this entry } }
	 * 
	 * // Insert user insertStmt.setString(1, user[0]); // user_id
	 * insertStmt.setString(2, user[1]); // email insertStmt.setString(3, user[2]);
	 * // username insertStmt.setString(4, user[3]); // password
	 * insertStmt.setString(5, user[4]); // first_name insertStmt.setString(6,
	 * user[5]); // last_name insertStmt.setBoolean(7,
	 * Boolean.parseBoolean(user[6])); // is_verified insertStmt.setString(8,
	 * user[7]); // role_id insertStmt.executeUpdate(); } } }
	 */

    
    private void insertDefaultRoles(Connection conn) throws SQLException {
        String checkRoleSql = "SELECT COUNT(*) FROM " + ROLES_TABLE + " WHERE role_name = ?";
        String insertRoleSql = "INSERT INTO " + ROLES_TABLE + " (role_id, role_name, description) VALUES (?, ?, ?)";

        try (
            PreparedStatement checkStmt = conn.prepareStatement(checkRoleSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertRoleSql);
        ) {
            // Array of default roles to insert
            String[][] defaultRoles = {
                {"35ea87d3-7df2-4cf3-9e4c-d326027cbe95", "sysAdmin", "Administrator role with full access"},
                {"e45cfed3-ed61-49ba-9215-2c44ee23bdae", "member", "Regular user role with access to view and reserve books"}
            };

            for (String[] role : defaultRoles) {
                // Check if role already exists
                checkStmt.setString(1, role[1]);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Role does not exist; Insert default roles
                        insertStmt.setString(1, role[0]); // role_id
                        insertStmt.setString(2, role[1]); // role_name
                        insertStmt.setString(3, role[2]); // description
                        insertStmt.executeUpdate();
                        System.out.println("Inserted default role: " + role[1]);
                    }
                }
            }
        }
    }
    
    private void insertDefaultUserRole(Connection conn) throws SQLException {
        String checkRoleSql = "SELECT role_id FROM " + ROLES_TABLE + " WHERE role_id = ?";
        String insertUserRoleSql = "INSERT INTO " + USER_ROLE_TABLE + " (user_role_id, user_id, role_id, assigned_date, status) VALUES (?, ?, ?, ?, ?)";

        try (
            PreparedStatement checkRoleStmt = conn.prepareStatement(checkRoleSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertUserRoleSql);
        ) {
            String[][] defaultUserRoles = {
                {"694e6342-b380-11ef-a3c7-325096b39f47", "5ac66fc0-b37d-11ef-bf20-325096b39f47", "35ea87d3-7df2-4cf3-9e4c-d326027cbe95", "Active"}, // Admin
                {"70c5c1ba-b380-11ef-be12-325096b39f47", "5ac67524-b37d-11ef-a678-325096b39f47", "e45cfed3-ed61-49ba-9215-2c44ee23bdae", "Active"}  // Member
            };

            for (String[] userRole : defaultUserRoles) {
                // Validate role_id exists
                checkRoleStmt.setString(1, userRole[2]);
                try (ResultSet roleRs = checkRoleStmt.executeQuery()) {
                    if (!roleRs.next()) {
                        System.err.println("Role ID does not exist: " + userRole[2]);
                        continue; // Skip this entry
                    }
                }

                System.out.println("Skipping role assignment for user ID: " + userRole[1] + " (user not created)");
            }
        }
    }

    
    private static boolean dbExists(String dbName, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(dbName)) return true;
        }
        return false;
    }

    public boolean tableExists(Connection conn, String tableName) throws SQLException {
        return conn.getMetaData().getTables(null, null, tableName, new String[] {"TABLE"}).next();
    }
	

}
