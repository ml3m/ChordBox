package com.chordbox.services;

import com.chordbox.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }
    public String getCustomerCount() {
        // Implement SQL query to count all customers
        return "150";  // Replace with actual count from DB
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Customer added: " + customer.getName());
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
