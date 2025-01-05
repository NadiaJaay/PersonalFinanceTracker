package com.PersonalFinance.dao;

import com.PersonalFinance.beans.Goal;
import com.PersonalFinance.libs.UUIDGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDao {

    // Add a new goal
    public static String addGoal(String userId, String goalName, float targetAmount, float currentAmount, 
                                  String description, boolean isComplete, Date endDate) {
        String goalId = UUIDGenerator.generateUUID();
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "INSERT INTO savings (goal_id, user_id, goal_name, target_amount, current_amount, " +
                         "description, is_completed, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, goalId);
            stmt.setString(2, userId);
            stmt.setString(3, goalName);
            stmt.setFloat(4, targetAmount);
            stmt.setFloat(5, currentAmount);
            stmt.setString(6, description);
            stmt.setBoolean(7, isComplete);
            stmt.setDate(8, endDate);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Goal added successfully. Goal ID: " + goalId);
                return goalId;
            } else {
                System.err.println("Failed to add goal.");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch goals based on completion status
    public static List<Goal> fetchGoalsByStatus(String userId, Boolean isCompleted) {
        List<Goal> goals = new ArrayList<>();
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "SELECT goal_id, goal_name, target_amount, current_amount, description, is_completed, end_date " +
                         "FROM savings WHERE user_id = ?";
            if (isCompleted != null) {
                sql += " AND is_completed = ?";
            }
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, userId);
            if (isCompleted != null) {
                stmt.setBoolean(2, isCompleted);
            }

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Goal goal = new Goal();
                goal.setGoal_id(resultSet.getString("goal_id"));
                goal.setUser_id(userId);
                goal.setGoalName(resultSet.getString("goal_name"));
                goal.setTargetAmount(resultSet.getFloat("target_amount"));
                goal.setCurrentAmount(resultSet.getFloat("current_amount"));
                goal.setDescription(resultSet.getString("description"));
                goal.setIsComplete(resultSet.getBoolean("is_completed"));
                goal.setEndDate(resultSet.getDate("end_date"));

                goals.add(goal);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return goals;
    }

    // Fetch a single goal by ID
    public static Goal fetchGoalById(String goalId) {
        Goal goal = null;
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "SELECT goal_id, user_id, goal_name, target_amount, current_amount, description, " +
                         "is_completed, end_date FROM savings WHERE goal_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, goalId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                goal = new Goal();
                goal.setGoal_id(resultSet.getString("goal_id"));
                goal.setUser_id(resultSet.getString("user_id"));
                goal.setGoalName(resultSet.getString("goal_name"));
                goal.setTargetAmount(resultSet.getFloat("target_amount"));
                goal.setCurrentAmount(resultSet.getFloat("current_amount"));
                goal.setDescription(resultSet.getString("description"));
                goal.setIsComplete(resultSet.getBoolean("is_completed"));
                goal.setEndDate(resultSet.getDate("end_date"));
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return goal;
    }

    // Update an existing goal
    public static boolean updateGoal(String goalId, String goalName, float targetAmount, float currentAmount, 
                                     String description, boolean isComplete, Date endDate) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "UPDATE savings SET goal_name = ?, target_amount = ?, current_amount = ?, description = ?, " +
                         "is_completed = ?, end_date = ? WHERE goal_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, goalName);
            stmt.setFloat(2, targetAmount);
            stmt.setFloat(3, currentAmount);
            stmt.setString(4, description);
            stmt.setBoolean(5, isComplete);
            stmt.setDate(6, endDate);
            stmt.setString(7, goalId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Goal updated successfully. Goal ID: " + goalId);
                return true;
            } else {
                System.err.println("No goal found with ID: " + goalId);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a goal
    public static boolean deleteGoal(String goalId) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "DELETE FROM savings WHERE goal_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, goalId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Goal deleted successfully. Goal ID: " + goalId);
                return true;
            } else {
                System.err.println("No goal found with ID: " + goalId);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // Fetch percentage of completed goals
    public static float fetchGoalCompletionPercentage(String userId) {
        String sql = "SELECT " +
                     "(SELECT COUNT(*) FROM savings WHERE user_id = ? AND is_completed = true) AS completed_count, " +
                     "(SELECT COUNT(*) FROM savings WHERE user_id = ?) AS total_count";

        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, userId);
            stmt.setString(2, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int completedCount = resultSet.getInt("completed_count");
                int totalCount = resultSet.getInt("total_count");

                if (totalCount == 0) return 0; // Avoid division by zero
                return (float) completedCount / totalCount * 100;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
