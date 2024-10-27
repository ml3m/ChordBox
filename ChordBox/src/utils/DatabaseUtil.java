package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import utils.DatabaseUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseUtil {
    private static final String INVENTORY_DB_URL = "jdbc:sqlite:inventory.db";
    private static final String TRANSACTIONS_DB_URL = "jdbc:sqlite:transactions.db";

    // Method to get a connection to the Inventory database
    public static Connection getInventoryConnection() throws SQLException {
        return DriverManager.getConnection(INVENTORY_DB_URL);
    }

    // Method to get a connection to the Transactions database
    public static Connection getTransactionsConnection() throws SQLException {
        return DriverManager.getConnection(TRANSACTIONS_DB_URL);
    }

    // Initialize the Inventory database and create its tables
    public static void initializeInventoryDatabase() {
        String createItemsTableSQL = """
            CREATE TABLE IF NOT EXISTS Items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    price REAL NOT NULL,
                    type TEXT NOT NULL
                    );
        """;

        try (Connection conn = getInventoryConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createItemsTableSQL);
            System.out.println("Inventory database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize inventory database: " + e.getMessage());
        }
    }

    // Initialize the Transactions database and create its tables
    public static void initializeTransactionsDatabase() {
        String createTransactionsTableSQL = """
            CREATE TABLE IF NOT EXISTS Transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    transaction_id TEXT NOT NULL,
                    customer_name TEXT NOT NULL,
                    customer_email TEXT,
                    payment_method TEXT NOT NULL,
                    total_amount REAL NOT NULL,
                    transaction_date TEXT NOT NULL
                    );
        """;

        try (Connection conn = getTransactionsConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTransactionsTableSQL);
            System.out.println("Transactions database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize transactions database: " + e.getMessage());
        }
    }

    // Save a transaction to the Transactions database
    public static void saveTransaction(String transactionId, String customerName, String customerEmail,
            String paymentMethod, double totalAmount, String transactionDate) {
        String insertTransactionSQL = """
            INSERT INTO Transactions (transaction_id, customer_name, customer_email, payment_method, total_amount, transaction_date)
            VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = getTransactionsConnection(); PreparedStatement pstmt = conn.prepareStatement(insertTransactionSQL)) {
            pstmt.setString(1, transactionId);
            pstmt.setString(2, customerName);
            pstmt.setString(3, customerEmail);
            pstmt.setString(4, paymentMethod);
            pstmt.setDouble(5, totalAmount);
            pstmt.setString(6, transactionDate);
            pstmt.executeUpdate();
            System.out.println("Transaction saved to transactions database.");
        } catch (SQLException e) {
            System.err.println("Failed to save transaction: " + e.getMessage());
        }
    }

    private static final String USER_DB_URL = "jdbc:sqlite:users.db";

    // Method to get a connection to the User database
    public static Connection getUserConnection() throws SQLException {
        return DriverManager.getConnection(USER_DB_URL);
    }

    // Initialize the User database and create the Users table
    public static void initializeUserDatabase() {
        String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS Users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    email TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    role TEXT NOT NULL
                    );
        """;

        try (Connection conn = getUserConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTableSQL);
            System.out.println("User database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize user database: " + e.getMessage());
        }
    }

    // Hashing password with SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.");
        }
    }
}