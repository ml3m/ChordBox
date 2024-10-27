package services;

import models.Order;

import java.util.List;

public class ReportService {
    private OrderService orderService;

    public ReportService() {
        this.orderService = new OrderService();
    }

    public String generateSalesReport() {
        StringBuilder report = new StringBuilder();
        List<Order> orders = orderService.getOrders();

        double totalRevenue = 0;
        report.append("Sales Report:\n");
        report.append("=============\n");

        for (Order order : orders) {
            double discountedPrice = order.applyDiscount();
            totalRevenue += discountedPrice;
            report.append(String.format("Order: %s - Price after discount: $%.2f\n", order.getItem().getName(), discountedPrice));
        }

        report.append("=============\n");
        report.append(String.format("Total Revenue: $%.2f\n", totalRevenue));

        return report.toString();
    }
}
