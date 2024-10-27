package gui;

import models.Customer;
import services.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPanel extends JPanel {
    private CustomerService customerService;
    private DefaultListModel<String> customerListModel;

    public CustomerPanel() {
        this.customerService = new CustomerService();
        setLayout(new BorderLayout());

        // Input panel for adding a new customer
        JPanel addCustomerPanel = new JPanel(new GridLayout(3, 2));
        JTextField customerNameField = new JTextField();
        JTextField customerEmailField = new JTextField();
        JButton addCustomerButton = new JButton("Add Customer");

        addCustomerPanel.add(new JLabel("Customer Name:"));
        addCustomerPanel.add(customerNameField);
        addCustomerPanel.add(new JLabel("Customer Email:"));
        addCustomerPanel.add(customerEmailField);
        addCustomerPanel.add(addCustomerButton);

        add(addCustomerPanel, BorderLayout.NORTH);

        // Customer list display
        customerListModel = new DefaultListModel<>();
        JList<String> customerList = new JList<>(customerListModel);
        JScrollPane scrollPane = new JScrollPane(customerList);
        add(scrollPane, BorderLayout.CENTER);

        // Add customer action
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }
}
