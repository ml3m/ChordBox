package com.chordbox.models;

import java.util.Date;
import java.util.List;

public class Transaction {
    private String transactionId;
    private Customer customer;
    private List<Order> orders;
    private Payment payment;
    private Date transactionDate;
    private double totalAmount;

    public Transaction(String transactionId, Customer customer, List<Order> orders, Payment payment, Date transactionDate) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.orders = orders;
        this.payment = payment;
        this.transactionDate = transactionDate;
        this.totalAmount = calculateTotalAmount();
    }

    private double calculateTotalAmount() {
        return orders.stream()
                .mapToDouble(Order::applyDiscount)
                .sum();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Payment getPayment() {
        return payment;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void printTransactionDetails() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Date: " + transactionDate);
        System.out.println("Customer: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.println(" - " + order.getItem().getName() + " - $" + order.applyDiscount());
        }
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Payment Method: " + payment.getMethod());
        System.out.println("Amount Paid: $" + payment.getAmountPaid());
    }
}
