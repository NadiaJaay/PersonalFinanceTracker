package com.PersonalFinance.beans;

import java.sql.Timestamp;
import java.util.UUID;

public class UserRole {
	private String user_role_id;
	private String user_id;
	private String role_id;
	private Timestamp assigned_date;
	private String status;
	private String roleName;



    public UserRole(String user_role_id, String user_id, String role_id,Timestamp assigned_date, String status, String roleName) {
    	super();
    	this.user_role_id= user_role_id;
    	this.user_id=user_id;
    	this.role_id = role_id;
    	this.assigned_date = assigned_date;
    	this.status = status;
    	this.roleName = roleName;
    }
    
    @Override
    public String toString() {
        return "UserRole{" +
               "user_role_id='" + user_role_id + '\'' +
               ", user_id='" + user_id + '\'' +
               ", role_id='" + role_id + '\'' +
               ", assigned_date=" + assigned_date +
               ", status='" + status + '\'' +
               ", roleName='" + roleName + '\'' +
               '}';
    }
    
    public void setUser_role_id() {
    	this.user_role_id = user_role_id;
    }
    
    public String getUser_role_id() {
    	return user_role_id;
    }
    public void setUser_id(String user_id) {
    	this.user_id = user_id;
    }
    public String getUser_id() {
    	return user_id;
    }
    public void setRole_id(String role_id) {
        this.role_id=role_id;
    }
    public String getRole_id() {
    	return role_id;
    }
    public void setAssigned_date(Timestamp assigned_date) {
    	this.assigned_date = assigned_date;
    }
    public Timestamp getAssigned_date() {
    	return assigned_date;
    }
    
    public void setStatus(String status) {
        this.status=status;
    }
    public String getStatus() {
    	return status;
    }
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
