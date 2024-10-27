import gui.MusicStoreGUI;

public class MusicStoreSystem {
    public static void main(String[] args) {
        MusicStoreGUI.main(args);
    }
}
package utils;

import java.util.Random;

public class InputDevice {
    private Random random;

    public InputDevice() {
        this.random = new Random();
    }

    public String getType() {
        return "random";
    }

    public Integer nextInt() {
        return random.nextInt(100) + 1;
    }

    public String getLine() {
        return "The quick brown fox jumps over the lazy dog.";
    }

    public Integer[] getNumbers(int N) {
        Integer[] numArray = new Integer[N];
        for (int i = 0; i < N; i++) {
            numArray[i] = nextInt();
        }
        return numArray;
    }
}
package utils;

public class OutputDevice {
    public void writeMessage(String message) {
        System.out.println(message);
    }
}
package utils;

public interface Discountable {
    double applyDiscount();
}
package utils;

public interface Sellable {
    void sell();
}
package models;

import utils.Discountable;

import java.util.List;

public class Order implements Discountable {
    private Item item;
    private List<Discount> discounts;

    public Order(Item item, List<Discount> discounts) {
        this.item = item;
        this.discounts = discounts;
    }

    @Override
    public double applyDiscount() {
        double totalDiscount = 0;
        for (Discount discount : discounts) {
            totalDiscount += discount.calculateDiscount(item.getPrice());
        }
        return item.getPrice() - totalDiscount;
    }

    public Item getItem() {
        return item;
    }
}
package models;

import utils.Sellable;

public class Poster extends Item implements Sellable {
    public Poster(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling poster: " + getName() + " for $" + getPrice());
    }
}
package models;

import utils.Sellable;

public class Disk extends Item implements Sellable {
    public Disk(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling disk: " + getName() + " for $" + getPrice());
    }
}
package models;

public class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
package models;

public class Payment {
    private String method;
    private double amountPaid;

    public Payment(String method, double amountPaid) {
        this.method = method;
        this.amountPaid = amountPaid;
    }

    public void processPayment() {
        System.out.println("Processing payment with " + method + " for $" + amountPaid);
    }
}
package models;

public class Discount {
    private double amount;
    private String type;

    public Discount(double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public double calculateDiscount(double price) {
        if (type.equalsIgnoreCase("percentage")) {
            return price * (amount / 100);
        } else if (type.equalsIgnoreCase("flat")) {
            return amount;
        }
        return 0;
    }

    public String getType() {
        return type;
    }
}
package models;

import utils.Sellable;

public class CD extends Item implements Sellable {
    public CD(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling CD: " + getName() + " for $" + getPrice());
    }
}
package models;

import utils.Sellable;

public class Instrument extends Item implements Sellable {
    public Instrument(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling instrument: " + getName() + " for $" + getPrice());
    }
}
package models;

public abstract class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
package gui;

import models.*;
import services.InventoryService;
import utils.OutputDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicStoreGUI extends JFrame {
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JComboBox<String> itemTypeCombo;
    private DefaultListModel<String> inventoryListModel;
    private InventoryService inventoryService;

    public MusicStoreGUI() {
        inventoryService = new InventoryService();
        setTitle("Music Store Inventory Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Item Name:"));
        itemNameField = new JTextField();
        inputPanel.add(itemNameField);

        inputPanel.add(new JLabel("Item Price:"));
        itemPriceField = new JTextField();
        inputPanel.add(itemPriceField);

        inputPanel.add(new JLabel("Item Type:"));
        itemTypeCombo = new JComboBox<>(new String[]{"CD", "Disk", "Instrument", "Poster"});
        inputPanel.add(itemTypeCombo);

        JButton addButton = new JButton("Add Item");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);
        inventoryListModel = new DefaultListModel<>();
        JList<String> inventoryList = new JList<>(inventoryListModel);
        JScrollPane listScrollPane = new JScrollPane(inventoryList);
        add(listScrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToInventory();
            }
        });
    }

    private void addItemToInventory() {
        String name = itemNameField.getText();
        String priceText = itemPriceField.getText();
        String type = (String) itemTypeCombo.getSelectedItem();

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
            itemNameField.setText("");
            itemPriceField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MusicStoreGUI gui = new MusicStoreGUI();
            gui.setVisible(true);
        });
    }
}
package services;

import models.Customer;
import models.Order;
import models.Payment;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;
    private List<Customer> customers;

    public OrderService() {
        orders = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public void placeOrder(Order order, Customer customer) {
        orders.add(order);
        customers.add(customer);
        System.out.println("Order placed for customer: " + customer.getName());
    }

    public void listOrders() {
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.println(" - " + order.getItem().getName() + " after discount: $" + order.applyDiscount());
        }
    }
}
package services;

import models.Item;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private List<Item> inventory;

    public InventoryService() {
        this.inventory = new ArrayList<>();
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Added item to inventory: " + item.getName());
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void listInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Item item : inventory) {
                System.out.println(" - " + item.getName() + " ($" + item.getPrice() + ")");
            }
        }
    }
}
