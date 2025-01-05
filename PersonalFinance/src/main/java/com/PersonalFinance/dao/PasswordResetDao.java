package com.PersonalFinance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.PersonalFinance.dao.DBConnection;
import com.PersonalFinance.dao.DBUtil;
import java.sql.Timestamp;
import java.util.UUID;

public class PasswordResetDao {

    public static void saveResetToken(String userId, String token) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "INSERT INTO password_reset (reset_id, user_id, token, created_at, status) VALUES (?, ?, ?, ?, 'pending')";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, userId);
            statement.setString(3, token);
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Validate token
    public static boolean validateToken(String token) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT reset_id FROM password_reset WHERE token = ? AND status = 'pending'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, token);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get user ID by token
    public static String getUserIdByToken(String token) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT user_id FROM password_reset WHERE token = ? AND status = 'pending'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, token);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("user_id");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mark token as completed
    public static void markTokenAsCompleted(String token) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "UPDATE password_reset SET status = 'completed' WHERE token = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, token);

            statement.executeUpdate();
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
