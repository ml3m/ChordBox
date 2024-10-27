package gui;

import models.*;
import services.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JPanel addItemPanel = new JPanel(new GridLayout(5, 2));
        nameField = new JTextField();
        priceField = new JTextField();
        typeComboBox = new JComboBox<>(new String[]{"CD", "Disk", "Instrument", "Poster"});
        JButton addItemButton = new JButton("Add Item");
        
        addItemPanel.add(new JLabel("Item Name:"));
        addItemPanel.add(nameField);
        addItemPanel.add(new JLabel("Item Price:"));
        addItemPanel.add(priceField);
        addItemPanel.add(new JLabel("Item Type:"));
        addItemPanel.add(typeComboBox);
        addItemPanel.add(addItemButton);

        // Inventory list display
        inventoryListModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryListModel);
        JScrollPane scrollPane = new JScrollPane(inventoryList);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for search and delete functionalities
        JButton searchItemButton = new JButton("Search Item");
        JButton deleteItemButton = new JButton("Delete Selected Item");
        JPanel actionPanel = new JPanel(new GridLayout(1, 2));
        actionPanel.add(searchItemButton);
        actionPanel.add(deleteItemButton);

        // Add panels to main layout
        add(addItemPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.SOUTH);

        // Add item button action
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItemToInventory();
            }
        });

        // Search item button action
        searchItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchItemInInventory();
            }
        });

        // Delete item button action
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItemFromInventory();
            }
        });
    }

    // Method to add item to inventory
    private void addItemToInventory() {
        String name = nameField.getText();
        String priceText = priceField.getText();
        String type = (String) typeComboBox.getSelectedItem();

        try {
            double price = Double.parseDouble(priceText);
            Item item;

            switch (type) {
                case "CD":
                    item = new CD(name, price);
                    break;
                case "Disk":
                    item = new Disk(name, price);
                    break;
                case "Instrument":
                    item = new Instrument(name, price);
                    break;
                case "Poster":
                    item = new Poster(name, price);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid item type");
            }

            inventoryService.addItem(item);
            inventoryListModel.addElement(item.getName() + " - $" + item.getPrice() + " (" + type + ")");
            nameField.setText("");
            priceField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
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

    // Method to delete the selected item from the inventory
    private void deleteSelectedItemFromInventory() {
        String selectedItem = inventoryList.getSelectedValue();
        if (selectedItem != null) {
            String itemName = selectedItem.split(" - ")[0];
            inventoryService.findItemByName(itemName).ifPresent(item -> {
                inventoryService.deleteItem(item);
                inventoryListModel.removeElement(selectedItem);
                JOptionPane.showMessageDialog(this, "Item deleted: " + item.getName(), "Delete Result", JOptionPane.INFORMATION_MESSAGE);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
