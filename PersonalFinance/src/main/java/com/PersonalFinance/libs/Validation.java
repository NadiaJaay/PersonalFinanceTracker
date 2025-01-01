package com.PersonalFinance.libs;

import java.util.Map;

import javax.servlet.http.HttpServlet;

public abstract class Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final String emailRegEx = "\\S+@\\S+\\.\\S+";
	
	protected boolean isEmpty(String value) {
		return value == null || value.equals("");
	}
	
	protected boolean isEmpty(String[] values) {
		return values == null || values.length == 0;
	}
	
	protected abstract String validateForm(Map<String,String[]> params);
}
