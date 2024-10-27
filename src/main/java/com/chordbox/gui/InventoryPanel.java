package com.chordbox.gui;

import com.chordbox.models.*;
import com.chordbox.services.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

public class InventoryPanel extends JPanel {
    private InventoryService inventoryService;
    private DefaultListModel<String> inventoryListModel;
    private JTextField nameField;
    private JTextField priceField;
    private JComboBox<String> typeComboBox;
    private JList<String> inventoryList;

    public InventoryPanel() {
        this.inventoryService = new InventoryService();
        setLayout(new BorderLayout());

        // Input panel for adding items
        JPanel addItemPanel = new JPanel(new GridBagLayout());
        addItemPanel.setBorder(BorderFactory.createTitledBorder("Add New Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        addItemPanel.add(new JLabel("Item Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        addItemPanel.add(nameField, gbc);

        priceField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        addItemPanel.add(new JLabel("Item Price:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        addItemPanel.add(priceField, gbc);

        typeComboBox = new JComboBox<>(new String[]{"CD", "Disk", "Instrument", "Poster"});
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        addItemPanel.add(new JLabel("Item Type:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        addItemPanel.add(typeComboBox, gbc);

        JButton addItemButton = new JButton("Add Item");
        JButton viewInventoryButton = new JButton("View Inventory");
        addItemButton.putClientProperty("JButton.arc", 20);
        viewInventoryButton.putClientProperty("JButton.arc", 20);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(addItemButton);
        buttonPanel.add(viewInventoryButton);
        addItemPanel.add(buttonPanel, gbc);

        add(addItemPanel, BorderLayout.NORTH);

        // Inventory list display
        inventoryListModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryListModel);
        JScrollPane scrollPane = new JScrollPane(inventoryList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Inventory List"));
        add(scrollPane, BorderLayout.CENTER);

        // Load initial inventory data
        refreshInventoryList();

        // Action buttons panel for search, edit, and delete functionalities
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 

        JButton searchItemButton = new JButton("Search Item");
        JButton editItemButton = new JButton("Edit Selected Item");  // New Edit button
        JButton deleteItemButton = new JButton("Delete Selected Item");

        searchItemButton.putClientProperty("JButton.arc", 20);
        editItemButton.putClientProperty("JButton.arc", 20);
        deleteItemButton.putClientProperty("JButton.arc", 20);

        actionPanel.add(Box.createHorizontalGlue());
        actionPanel.add(searchItemButton);
        actionPanel.add(Box.createRigidArea(new Dimension(10, 0))); 
        actionPanel.add(editItemButton); // Added edit button to panel
        actionPanel.add(Box.createRigidArea(new Dimension(10, 0))); 
        actionPanel.add(deleteItemButton);
        actionPanel.add(Box.createHorizontalGlue());

        add(actionPanel, BorderLayout.SOUTH);

        // Add button actions
        addItemButton.addActionListener(this::addItemToInventory);
        viewInventoryButton.addActionListener(this::showInventoryDialog);
        searchItemButton.addActionListener(e -> searchItemInInventory());
        editItemButton.addActionListener(e -> editSelectedItem()); // Edit action
        deleteItemButton.addActionListener(e -> deleteSelectedItemFromInventory());
    }

    // Method to add item to inventory and update the list
    private void addItemToInventory(ActionEvent e) {
        String name = nameField.getText();
        String priceText = priceField.getText();
        String type = (String) typeComboBox.getSelectedItem();

        try {
            double price = Double.parseDouble(priceText);
            Item item;

            switch (type) {
                case "CD": item = new CD(name, price); break;
                case "Disk": item = new Disk(name, price); break;
                case "Instrument": item = new Instrument(name, price); break;
                case "Poster": item = new Poster(name, price); break;
                default: throw new IllegalArgumentException("Invalid item type");
            }

            inventoryService.addItem(item);
            refreshInventoryList(); // Refresh list after adding item
            nameField.setText("");
            priceField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to edit the selected item in the inventory
    // Method to edit the selected item in the inventory
    private void editSelectedItem() {
        String selectedItem = inventoryList.getSelectedValue();
        if (selectedItem != null) {
            String originalName = selectedItem.split(" - ")[0];
            Optional<Item> itemOpt = inventoryService.findItemByName(originalName);

            if (itemOpt.isPresent()) {
                Item originalItem = itemOpt.get();

                // Prompt user for new details
                String newName = JOptionPane.showInputDialog(this, "Edit Item Name:", originalItem.getName());
                if (newName == null || newName.trim().isEmpty()) {
                    return; // Cancel if the new name is empty
                }

                String newPriceText = JOptionPane.showInputDialog(this, "Edit Item Price:", originalItem.getPrice());
                double newPrice;
                try {
                    newPrice = Double.parseDouble(newPriceText);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] itemTypes = {"CD", "Disk", "Instrument", "Poster"};
                String newType = (String) JOptionPane.showInputDialog(this, "Edit Item Type:", "Edit Type",
                        JOptionPane.QUESTION_MESSAGE, null, itemTypes, originalItem.getClass().getSimpleName());
                if (newType == null) {
                    return; // Cancel if no type selected
                }

                // Create updated item
                Item updatedItem = createNewItem(newType, newName, newPrice);

                // Update item in inventory
                boolean updated = inventoryService.updateItem(originalName, updatedItem);
                if (updated) {
                    refreshInventoryList(); // Refresh list after editing
                    JOptionPane.showMessageDialog(this, "Item updated successfully!", "Update", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Item not found. Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper to create item of specific type
    private Item createNewItem(String type, String name, double price) {
        switch (type) {
            case "CD": return new CD(name, price);
            case "Disk": return new Disk(name, price);
            case "Instrument": return new Instrument(name, price);
            case "Poster": return new Poster(name, price);
            default: throw new IllegalArgumentException("Invalid item type");
        }
    }

    // Method to delete the selected item from the inventory and update the list
    private void deleteSelectedItemFromInventory() {
        String selectedItem = inventoryList.getSelectedValue();
        if (selectedItem != null) {
            String itemName = selectedItem.split(" - ")[0];
            inventoryService.findItemByName(itemName).ifPresent(item -> {
                inventoryService.deleteItem(item);
                refreshInventoryList(); // Refresh list after deletion
                JOptionPane.showMessageDialog(this, "Item deleted: " + item.getName(), "Delete Result", JOptionPane.INFORMATION_MESSAGE);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to refresh the inventory list from the service
    private void refreshInventoryList() {
        inventoryListModel.clear();
        List<Item> items = inventoryService.getInventory();
        for (Item item : items) {
            inventoryListModel.addElement(item.getName() + " - $" + item.getPrice() + " (" + item.getClass().getSimpleName() + ")");
        }
    }

    // Method to search for an item in the inventory
    private void searchItemInInventory() {
        String itemName = JOptionPane.showInputDialog(this, "Enter item name to search:");
        if (itemName != null && !itemName.isEmpty()) {
            inventoryService.findItemByName(itemName).ifPresentOrElse(
                    item -> JOptionPane.showMessageDialog(this, "Item found: " + item.getName() + " - $" + item.getPrice(),
                        "Search Result", JOptionPane.INFORMATION_MESSAGE),
                    () -> JOptionPane.showMessageDialog(this, "Item not found.", "Search Result", JOptionPane.ERROR_MESSAGE)
                    );
        }
    }

    // Method to show the entire inventory in a dialog
    private void showInventoryDialog(ActionEvent e) {
        JDialog inventoryDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Full Inventory", true);
        inventoryDialog.setSize(600, 400);
        inventoryDialog.setLayout(new BorderLayout());

        List<Item> items = inventoryService.getInventory();
        String[] columnNames = {"Item Name", "Price", "Type"};
        Object[][] data = new Object[items.size()][3];

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            data[i][0] = item.getName();
            data[i][1] = "$" + item.getPrice();
            data[i][2] = item.getClass().getSimpleName();
        }

        JTable fullInventoryTable = new JTable(data, columnNames);
        inventoryDialog.add(new JScrollPane(fullInventoryTable), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.putClientProperty("JButton.arc", 20);
        closeButton.addActionListener(ev -> inventoryDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        inventoryDialog.add(buttonPanel, BorderLayout.SOUTH);

        inventoryDialog.setLocationRelativeTo(this);
        inventoryDialog.setVisible(true);
    }
}
