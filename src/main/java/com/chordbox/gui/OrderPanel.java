package com.chordbox.gui;

import com.chordbox.models.Customer;
import com.chordbox.models.Order;
import com.chordbox.models.Payment;
import com.chordbox.models.Item;
import com.chordbox.models.Transaction;
import com.chordbox.services.InventoryService;
import com.chordbox.services.OrderService;
import com.chordbox.utils.DatabaseUtil;

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
        this.inventoryService = new InventoryService();

        setLayout(new BorderLayout());

        // Create main input panel with GridBagLayout for more precise control
        JPanel orderInputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Margins around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Define field sizes
        Dimension fieldSize = new Dimension(150, 20);
        
        // Customer Name
        customerNameField = new JTextField();
        customerNameField.setPreferredSize(fieldSize);
        customerNameField.setFont(new Font("Arial", Font.PLAIN, 12));  // Set a smaller font if necessary
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        orderInputPanel.add(new JLabel("Customer Name:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        orderInputPanel.add(customerNameField, gbc);

        // Customer Email
        customerEmailField = new JTextField();
        customerEmailField.setPreferredSize(fieldSize);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        orderInputPanel.add(new JLabel("Customer Email:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        orderInputPanel.add(customerEmailField, gbc);

        // Payment Method
        paymentMethodCombo = new JComboBox<>(new String[]{"Card", "Cash"});
        paymentMethodCombo.setPreferredSize(fieldSize);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        orderInputPanel.add(new JLabel("Payment Method:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        orderInputPanel.add(paymentMethodCombo, gbc);

        // Product Selection List
        productSelectionList = new JList<>(getInventoryItemNames());
        productSelectionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane productScrollPane = new JScrollPane(productSelectionList);
        productScrollPane.setPreferredSize(new Dimension(150, 80));  // Adjust height of list scroll pane

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        orderInputPanel.add(new JLabel("Select Products:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        orderInputPanel.add(productScrollPane, gbc);

        // Place the input panel in the NORTH section of the main panel
        add(orderInputPanel, BorderLayout.NORTH);

        // Orders list display
        ordersListModel = new DefaultListModel<>();
        JList<String> ordersList = new JList<>(ordersListModel);
        JScrollPane scrollPane = new JScrollPane(ordersList);
        add(scrollPane, BorderLayout.CENTER);

        // Place Order button at the bottom
        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(this::handlePlaceOrder);
        add(placeOrderButton, BorderLayout.SOUTH);
    }

    private DefaultListModel<String> getInventoryItemNames() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Item item : inventoryService.getInventory()) {
            model.addElement(item.getName() + " - $" + item.getPrice());
        }
        return model;
    }

    private void handlePlaceOrder(ActionEvent e) {
        String name = customerNameField.getText();
        String email = customerEmailField.getText();
        String paymentMethod = (String) paymentMethodCombo.getSelectedItem();

        Customer customer = new Customer(name, email);
        List<Item> selectedItems = getSelectedItems();
        if (selectedItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one product.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalAmount = calculateTotal(selectedItems);
        Payment payment = new Payment(paymentMethod, totalAmount);

        if (inventoryService.checkInventoryAvailability(selectedItems)) {
            Order order = new Order(selectedItems.get(0), null);
            orderService.placeOrder(order, customer);

            String transactionId = "TXN" + System.currentTimeMillis();
            Transaction transaction = new Transaction(transactionId, customer, List.of(order), payment, new java.util.Date());
            transaction.printTransactionDetails();

            String transactionDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            DatabaseUtil.saveTransaction(transactionId, customer.getName(), customer.getEmail(), paymentMethod, totalAmount, transactionDate);

            inventoryService.removeItems(selectedItems);
            updateProductSelection();

            ordersListModel.addElement("Order for " + customer.getName() + " - $" + payment.getAmountPaid());
        } else {
            JOptionPane.showMessageDialog(this, "Some items are out of stock.", "Inventory Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Item> getSelectedItems() {
        List<Item> selectedItems = new ArrayList<>();
        for (String selectedValue : productSelectionList.getSelectedValuesList()) {
            String itemName = selectedValue.split(" - ")[0];
            inventoryService.findItemByName(itemName).ifPresent(selectedItems::add);
        }
        return selectedItems;
    }

    private double calculateTotal(List<Item> items) {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    private void updateProductSelection() {
        productSelectionList.setModel(getInventoryItemNames());
    }
}
