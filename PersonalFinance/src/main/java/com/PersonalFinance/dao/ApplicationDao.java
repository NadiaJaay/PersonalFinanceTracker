package com.PersonalFinance.dao;

import java.sql.Connection;
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
	public static final String VERIFICATION_TABLE = "verification";

	
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
    	//TODO
    }
    
    public void createAccountsTable() {
    	//TODO
    }
    
    public void createTransactionTable() {
    	//TODO
    }
    
    public void createSavingsTable() {
    	//TODO
    }
    
    public void createRolesTable() {
    	
    }
    
    public void createVerificationTable() {
    	//TODO
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
