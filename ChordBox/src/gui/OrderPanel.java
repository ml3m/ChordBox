package gui;

import models.Customer;
import models.Order;
import models.Payment;
import models.Item;
import models.Transaction;
import services.InventoryService;
import services.OrderService;
import utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends JPanel {
    private OrderService orderService;
    private InventoryService inventoryService;

    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JComboBox<String> paymentMethodCombo;
    private JList<String> productSelectionList;
    private DefaultListModel<String> ordersListModel;

    public OrderPanel() {
        this.orderService = new OrderService();
        this.inventoryService = new InventoryService(); // Access inventory

        setLayout(new BorderLayout());

        // Order input panel
        JPanel orderInputPanel = new JPanel(new GridLayout(6, 2));
        customerNameField = new JTextField();
        customerEmailField = new JTextField();
        paymentMethodCombo = new JComboBox<>(new String[]{"Card", "Cash"});

        JButton placeOrderButton = new JButton("Place Order");

        // Set up product selection list
        productSelectionList = new JList<>(getInventoryItemNames());
        productSelectionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane productScrollPane = new JScrollPane(productSelectionList);

        orderInputPanel.add(new JLabel("Customer Name:"));
        orderInputPanel.add(customerNameField);
        orderInputPanel.add(new JLabel("Customer Email:"));
        orderInputPanel.add(customerEmailField);
        orderInputPanel.add(new JLabel("Payment Method:"));
        orderInputPanel.add(paymentMethodCombo);
        orderInputPanel.add(new JLabel("Select Products:"));
        orderInputPanel.add(productScrollPane);

        add(orderInputPanel, BorderLayout.NORTH);

        // Orders list display
        ordersListModel = new DefaultListModel<>();
        JList<String> ordersList = new JList<>(ordersListModel);
        JScrollPane scrollPane = new JScrollPane(ordersList);
        add(scrollPane, BorderLayout.CENTER);

        // Add button to place order
        placeOrderButton.addActionListener(this::handlePlaceOrder);
        add(placeOrderButton, BorderLayout.SOUTH);
    }

    // Populate product selection list from inventory
    private DefaultListModel<String> getInventoryItemNames() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Item item : inventoryService.getInventory()) {
            model.addElement(item.getName() + " - $" + item.getPrice());
        }
        return model;
    }

    // Handle order placement
    private void handlePlaceOrder(ActionEvent e) {
        String name = customerNameField.getText();
        String email = customerEmailField.getText();
        String paymentMethod = (String) paymentMethodCombo.getSelectedItem();

        // Create customer
        Customer customer = new Customer(name, email);

        // Process selected items
        List<Item> selectedItems = getSelectedItems();
        if (selectedItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one product.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate total payment amount
        double totalAmount = calculateTotal(selectedItems);

        // Create payment
        Payment payment = new Payment(paymentMethod, totalAmount);

        // Create and place order if items are available
        if (inventoryService.checkInventoryAvailability(selectedItems)) {
            Order order = new Order(selectedItems.get(0), null); // Assuming discounts are null for simplicity
            orderService.placeOrder(order, customer);

            // Create transaction
            String transactionId = "TXN" + System.currentTimeMillis();
            Transaction transaction = new Transaction(transactionId, customer, List.of(order), payment, new java.util.Date());
            transaction.printTransactionDetails();

            // Save transaction to database
            // Example usage in OrderPanel to save a transaction
            String transactionDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            DatabaseUtil.saveTransaction(transactionId, customer.getName(), customer.getEmail(), paymentMethod, totalAmount, transactionDate);

            // Remove ordered items from inventory
            inventoryService.removeItems(selectedItems);
            updateProductSelection();

            ordersListModel.addElement("Order for " + customer.getName() + " - $" + payment.getAmountPaid());
        } else {
            JOptionPane.showMessageDialog(this, "Some items are out of stock.", "Inventory Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Get selected items from inventory
    private List<Item> getSelectedItems() {
        List<Item> selectedItems = new ArrayList<>();
        for (String selectedValue : productSelectionList.getSelectedValuesList()) {
            String itemName = selectedValue.split(" - ")[0];
            inventoryService.findItemByName(itemName).ifPresent(selectedItems::add);
        }
        return selectedItems;
    }

    // Calculate total price of selected items
    private double calculateTotal(List<Item> items) {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    // Update product selection list after items are removed
    private void updateProductSelection() {
        productSelectionList.setModel(getInventoryItemNames());
    }
}
