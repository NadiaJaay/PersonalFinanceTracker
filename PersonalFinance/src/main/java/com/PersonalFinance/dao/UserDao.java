package com.PersonalFinance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.PersonalFinance.beans.User;
import com.PersonalFinance.dao.DBConnection;
import com.PersonalFinance.dao.DBUtil;
import com.PersonalFinance.libs.TokenGenerator;
import com.PersonalFinance.libs.UUIDGenerator;
import com.PersonalFinance.services.JavaEmailService;

public class UserDao {
	
	// Create user
	public static String createUser(String email, String username, String password, String first_name, String last_name, String role_id) {
	    UUIDGenerator uuidGenerator = new UUIDGenerator();
	    String user_id = uuidGenerator.generateUUID();

	    try (Connection connection = DBConnection.getDBInstance()) {
	        String create_user_sql = "INSERT INTO users (user_id, email, username, password, first_name, last_name, is_verified, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement preparedStmt = connection.prepareStatement(create_user_sql);
	        preparedStmt.setString(1, user_id);
	        preparedStmt.setString(2, email);
	        preparedStmt.setString(3, username);
	        preparedStmt.setString(4, password);
	        preparedStmt.setString(5, first_name);
	        preparedStmt.setString(6, last_name);
	        preparedStmt.setBoolean(7, false); // Default to not verified
	        preparedStmt.setString(8, role_id);

	        int rowsInserted = preparedStmt.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("User created successfully. User ID: " + user_id);
	            return user_id;
	        } else {
	            System.err.println("Failed to create user.");
	        }
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}



	// Save verification token
	public static void saveVerificationToken(String user_id, String verification_code) {
	    UUIDGenerator uuidGenerator = new UUIDGenerator();
	    String verification_id = uuidGenerator.generateUUID();

	    try (Connection connection = DBConnection.getDBInstance()) {
	        // Check if the user_id exists in the users table
	        String userExistsQuery = "SELECT user_id FROM users WHERE user_id = ?";
	        try (PreparedStatement checkStmt = connection.prepareStatement(userExistsQuery)) {
	            checkStmt.setString(1, user_id);
	            try (ResultSet rs = checkStmt.executeQuery()) {
	                if (!rs.next()) {
	                    System.err.println("User ID does not exist in users table: " + user_id);
	                    return; // Exit the method if the user_id does not exist
	                }
	            }
	        }

	        // Insert the verification token into the verification table
	        String sql = "INSERT INTO verification (verification_id, user_id, verification_code, verification_type, created_at, status) VALUES (?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, verification_id);
	            statement.setString(2, user_id);
	            statement.setString(3, verification_code);
	            statement.setString(4, "email");
	            statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
	            statement.setString(6, "pending");

	            statement.executeUpdate();
	        }

	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}


    // Verify user by token
    public static boolean verifyUserByToken(String verification_code) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT user_id FROM verification WHERE verification_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, verification_code);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String user_id = resultSet.getString("user_id");

                String updateSql = "UPDATE users SET is_verified = TRUE WHERE user_id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, user_id);
                updateStatement.executeUpdate();

                String updateRoleSql = "UPDATE user_role SET status = 'Active' WHERE user_id = ?";
                PreparedStatement updateRoleStatement = connection.prepareStatement(updateRoleSql);
                updateRoleStatement.setString(1, user_id);
                updateRoleStatement.executeUpdate();
                
                String updateVerificationSql = "UPDATE verification SET status = 'completed' WHERE verification_code = ?";
                PreparedStatement updateVerificationStmt = connection.prepareStatement(updateVerificationSql);
                updateVerificationStmt.setString(1, verification_code);
                updateVerificationStmt.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if username exists
    public static boolean usernameExists(String username) {
        boolean exists = false;

        try (Connection connection = DBConnection.getDBInstance()) {
            String username_exists_sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement preparedStmt = connection.prepareStatement(username_exists_sql);
            preparedStmt.setString(1, username);

            ResultSet rs = preparedStmt.executeQuery();
            if (rs != null && rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }

    // Check if email exists
    public static boolean emailExists(String email) {
        boolean exists = false;

        try (Connection connection = DBConnection.getDBInstance()) {
            String email_exists_sql = "SELECT user_id FROM users WHERE email=?";
            PreparedStatement preparedStmt = connection.prepareStatement(email_exists_sql);
            preparedStmt.setString(1, email);

            ResultSet rs = preparedStmt.executeQuery();

            if (rs != null && rs.next()) {
                String userId = rs.getString("user_id");
                if (userId != null && !userId.isEmpty()) {
                    exists = true;
                }
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public static User authenticateUser(String email, String password) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ? AND is_verified = TRUE";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                boolean isVerified = resultSet.getBoolean("is_verified");

                return new User(userId, username, firstName, lastName, isVerified);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String findIdByEmail(String email) {
        String userId = null;
        String query = "SELECT user_id FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getString("user_id");
                    if (userId == null || userId.isEmpty()) {
                        System.out.println("User ID is empty or null for email: " + email);
                    }
                } else {
                    System.out.println("No user found for email: " + email);
                }
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userId;
    }
    
 // Update user password
 	public static void updatePassword(String user_id, String newPassword) {
 	    try (Connection connection = DBConnection.getDBInstance()) {
 	        String query = "UPDATE users SET password = ? WHERE user_id = ?";
 	        PreparedStatement statement = connection.prepareStatement(query);
 	        statement.setString(1, newPassword); 
 	        statement.setString(2, user_id);

 	        statement.executeUpdate();
 	    } catch (SQLException e) {
 	        DBUtil.processException(e);
 	    } catch (ClassNotFoundException e) {
 	        e.printStackTrace();
 	    }
 	}
 	
 // Get user ID by email
 	public static String getUserIdByEmail(String email) {
 	    try (Connection connection = DBConnection.getDBInstance()) {
 	        String query = "SELECT user_id FROM users WHERE email = ?";
 	        PreparedStatement statement = connection.prepareStatement(query);
 	        statement.setString(1, email);

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


}
