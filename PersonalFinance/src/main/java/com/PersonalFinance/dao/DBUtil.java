package com.PersonalFinance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	public static final String USERNAME = "root";
	public static final String PASSWORD = "";
	private static String M_CONN_STRING = "jdbc:mysql://localhost/";
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		
		return DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
	}
	
	public static void setConnStr() {
		
		if (M_CONN_STRING.contains(ApplicationDao.DB_NAME))
			return;
		M_CONN_STRING += ApplicationDao.DB_NAME;
	}
	
	public static void processException(SQLException e) {
		System.err.println("Error message: " + e.getMessage());
		System.err.println("Error code: " + e.getErrorCode());
		System.err.println("SQL State: " + e.getSQLState());
		System.err.println("Trace project package: " + printRelevantStackTrace(e) + "\n");
	}
	
	private static String printRelevantStackTrace(Throwable e) {
		int i = 0;
		for (StackTraceElement element : e.getStackTrace()) {
			i++;
			if (element.getClassName().startsWith("com.pawpals")) {
				return ((String) "Relevant stack trace element[" + i + "]: " + element);
			}
		}
		return "not found";
	}

}
