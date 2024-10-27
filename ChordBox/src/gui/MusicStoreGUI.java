package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import models.User;
import services.AuthService;
import services.OrderService;
import services.InventoryService;
import services.CustomerService;
import utils.DatabaseUtil;

public class MusicStoreGUI extends JFrame {
    private User currentUser;
    private AuthService authService;
    private HomePanel homePanel;

    public MusicStoreGUI() {
        authService = new AuthService();

        setTitle("Music Store Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the user database
        DatabaseUtil.initializeUserDatabase();

        // Check if there are users in the system
        if (!authService.userExists("admin@default.com")) {
            authService.register("admin@default.com", "admin123", "admin");
        }

        loginPrompt();

        if (currentUser != null) {
            setupTabs();
            loadDataForDashboard();

            // Show welcome message based on user role
            if (authService.isAdmin(currentUser)) {
                JOptionPane.showMessageDialog(this, "Welcome Admin!");
            } else {
                JOptionPane.showMessageDialog(this, "Welcome, User!");
            }
        }
    }

    private void loginPrompt() {
        int choice = JOptionPane.showOptionDialog(this,
                "Do you want to Login or Register?",
                "Welcome",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Login", "Register"},
                "Login");

        if (choice == 1) {
            registerNewAccount();
            loginPrompt();  // Prompt for login after registration
            return;
        }

        String email = JOptionPane.showInputDialog(this, "Enter Email:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");

        currentUser = authService.login(email, password);

        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Login failed. Please try again.");
            System.exit(0);
        }
    }

    private void registerNewAccount() {
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JComboBox<String> roleField = new JComboBox<>(new String[]{"user", "admin"});

        Object[] message = {
                "Email:", emailField,
                "Password:", passwordField,
                "Confirm Password:", confirmPasswordField,
                "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Register Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String role = (String) roleField.getSelectedItem();

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
            } else if (authService.userExists(email)) {
                JOptionPane.showMessageDialog(this, "User already exists with this email.");
            } else if (authService.register(email, password, role)) {
                JOptionPane.showMessageDialog(this, "Account created successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create account.");
            }
        }
    }

    private void setupTabs() {
        // Initialize home panel and other panels
        homePanel = new HomePanel();

        // Tabbed Pane for different management functionalities
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Home", homePanel);  // Home tab added first
        tabbedPane.addTab("Inventory", new InventoryPanel());
        tabbedPane.addTab("Orders", new OrderPanel());
        
        // Only add Customers and Reports tabs if the user is an admin
        if (authService.isAdmin(currentUser)) {
            tabbedPane.addTab("Customers", new CustomerPanel());
            tabbedPane.addTab("Reports", new ReportPanel());
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Method to load data for the Home dashboard panel
    private void loadDataForDashboard() {
        OrderService orderService = new OrderService();
        InventoryService inventoryService = new InventoryService();
        CustomerService customerService = new CustomerService();

        homePanel.setTotalSales(orderService.getTotalSales());
        homePanel.setDailyRevenue(orderService.getDailyRevenue());
        homePanel.setInventoryCount(inventoryService.getInventoryCount());
        homePanel.setCustomerCount(customerService.getCustomerCount());
        homePanel.setTopSellingItem(orderService.getTopSellingItem());
        // Set additional data as needed
    }

    public static void main(String[] args) {
        // Set FlatLaf Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Button.arc", 20); // Set radius (adjust as desired)
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf Dark theme");
        }

        SwingUtilities.invokeLater(() -> new MusicStoreGUI().setVisible(true));
    }
}
