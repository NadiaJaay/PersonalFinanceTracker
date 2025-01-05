package com.PersonalFinance.beans;

import java.sql.Date;

public class Goal {
	private String goal_id;
	private String user_id;
	private String goalName;
	private float targetAmount;
	private float currentAmount;
	private String description;
	private boolean isComplete;
	private Date endDate;
	
	public Goal () {}
	
	public Goal (String goal_id, String user_id, String goalName, float targetAmount, float currentAmount, String description, boolean isComplete, Date endDate) {
		
		this.setGoal_id(goal_id);
		this.setUser_id(user_id);
		this.setGoalName(goalName);
		this.setTargetAmount(targetAmount);
		this.setCurrentAmount(currentAmount);
		this.setDescription(description);
		this.setIsComplete(isComplete);
		this.setEndDate(endDate);
	}

	public String getGoal_id() {
		return goal_id;
	}

	public void setGoal_id(String goal_id) {
		this.goal_id = goal_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public float getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(float targetAmount) {
		this.targetAmount = targetAmount;
	}

	public float getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(float currentAmount) {
		this.currentAmount = currentAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
