package com.PersonalFinance.beans;

public class User {
	private String user_id;
    private String email;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String address_id;
    private boolean is_verified;
    
    public User(String first_name, String last_name, String password, boolean is_verified) {
    	this.setFirst_name(first_name);
    	this.setLast_name(last_name);
    	this.setPassword(password);
    	this.setIs_verified(is_verified);
    }
    
    public User(String user_id, String username, String first_name, String last_name, boolean is_verified) {
    	this.setUser_id(user_id);
    	this.setUsername(username);
    	this.setFirst_name(first_name);
    	this.setLast_name(last_name);
    	this.setIs_verified(is_verified);
    }
    
    public User(String user_id, String username, String email, String password, String first_name, String last_name, boolean is_verified) {
    this.user_id = user_id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.first_name = first_name;
    this.last_name = last_name;
    this.address_id = address_id;
    this.is_verified = is_verified;
}
    
    public String getUser_id() {
    	return user_id;
    }
    public void setUser_id(String user_id) {
    	this.user_id = user_id;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public boolean getIs_verified() {
		return is_verified;
	}

	public void setIs_verified(boolean is_verified) {
		this.is_verified = is_verified;
	}
	
}
