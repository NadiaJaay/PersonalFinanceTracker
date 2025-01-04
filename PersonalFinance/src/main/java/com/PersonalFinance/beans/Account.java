package com.PersonalFinance.beans;

import java.sql.Timestamp;

public class Account {
	private String account_id;
	private String user_id;
	private String accountName;
	private String description;
	private float balance;
	private Timestamp createdAt;
	
	public Account() {}
	
	public Account(String account_id, String user_id, String accountName, String description, float balance, Timestamp createdAt) {
		this.setAccount_id(account_id);
		this.setUser_id(user_id);
		this.accountName = accountName;
		this.setDescription(description);
		this.setBalance(balance);
		this.createdAt = createdAt;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
