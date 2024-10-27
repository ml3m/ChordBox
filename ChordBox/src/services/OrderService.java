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

    // Inside OrderService.java
    public List<Order> getOrders() {
        return orders;
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
