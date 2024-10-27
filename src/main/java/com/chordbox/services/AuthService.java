package com.chordbox.services;

import com.chordbox.models.User;
import com.chordbox.utils.DatabaseUtil;

import java.sql.*;

public class AuthService {
    // Register a new user
    public boolean register(String email, String password, String role) {
        String hashedPassword = DatabaseUtil.hashPassword(password);

        String insertUserSQL = "INSERT INTO Users (email, password_hash, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getUserConnection(); PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    // Check if user already exists by email
    public boolean userExists(String email) {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (Connection conn = DatabaseUtil.getUserConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Failed to check user existence: " + e.getMessage());
            return false;
        }
    }

    // Login method to authenticate user and retrieve their role
    public User login(String email, String password) {
        String hashedPassword = DatabaseUtil.hashPassword(password);

        String query = "SELECT email, role FROM Users WHERE email = ? AND password_hash = ?";
        try (Connection conn = DatabaseUtil.getUserConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String userEmail = rs.getString("email");
                String userRole = rs.getString("role");
                System.out.println("Login successful for user: " + userEmail);
                return new User(userEmail, userRole);
            } else {
                System.out.println("Login failed. Invalid credentials.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    // Check if the user is an admin
    public boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }
}
