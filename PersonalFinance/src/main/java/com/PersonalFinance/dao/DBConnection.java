package com.PersonalFinance.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.PersonalFinance.dao.ApplicationDao;
import com.PersonalFinance.libs.DBType;

public class DBConnection {
	
	private static boolean initializedDB = false;
	
	private static void initDB() {
		if (initializedDB) { return; }
		initializedDB = true;
		ApplicationDao.createDatabase();
		ApplicationDao.getDao().createRolesTable();
		ApplicationDao.getDao().createUsersTable();
		ApplicationDao.getDao().createAccountsTable();
		ApplicationDao.getDao().createTransactionTable();
		ApplicationDao.getDao().createSavingsTable();
		ApplicationDao.getDao().createUserRoleTable();
		ApplicationDao.getDao().createVerificationTable();
		
	}	
	
    public static Connection getDBInstance() throws ClassNotFoundException {
    	initDB();
        Connection connection = null;
        try {
        	connection = DBUtil.getConnection(DBType.MYSQL);
        } catch (SQLException e) {
        	DBUtil.processException(e);
        };
        return connection;
    }
}