package gui;

import models.Customer;
import services.CustomerService;
import javax.swing.*;
import java.awt.*;

public class CustomerPanel extends JPanel {
    private CustomerService customerService;
    private DefaultListModel<String> customerListModel;

    public CustomerPanel() {
        this.customerService = new CustomerService();
        setLayout(new BorderLayout());

        // Panel for customer input form
        JPanel addCustomerPanel = new JPanel(new GridBagLayout());
        addCustomerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Outer padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components

        JTextField customerNameField = new JTextField(15);
        JTextField customerEmailField = new JTextField(15);
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.putClientProperty("JButton.arc", 20); // Rounded button corners

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        addCustomerPanel.add(new JLabel("Customer Name:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        addCustomerPanel.add(customerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        addCustomerPanel.add(new JLabel("Customer Email:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        addCustomerPanel.add(customerEmailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addCustomerPanel.add(addCustomerButton, gbc);

        add(addCustomerPanel, BorderLayout.NORTH);

        // Customer list display
        customerListModel = new DefaultListModel<>();
        JList<String> customerList = new JList<>(customerListModel);
        JScrollPane scrollPane = new JScrollPane(customerList);
        add(scrollPane, BorderLayout.CENTER);

        // Button action for adding a customer
        addCustomerButton.addActionListener(e -> {
            String name = customerNameField.getText();
            String email = customerEmailField.getText();

            if (!name.isEmpty() && !email.isEmpty()) {
                Customer customer = new Customer(name, email);
                customerService.addCustomer(customer);
                customerListModel.addElement(customer.getName() + " - " + customer.getEmail());
                customerNameField.setText("");
                customerEmailField.setText("");
            } else {
                JOptionPane.showMessageDialog(CustomerPanel.this, "Please fill in both name and email fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
