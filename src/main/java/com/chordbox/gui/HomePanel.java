package com.chordbox.gui;

import javax.swing.*;
import java.awt.*;

// Sample code for adding a chart (requires JFreeChart library)
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class HomePanel extends JPanel {
    private JLabel totalSalesLabel;
    private JLabel dailyRevenueLabel;
    private JLabel inventoryCountLabel;
    private JLabel customerCountLabel;
    private JLabel topSellingItemLabel;
    private JLabel activeEmployeesLabel;

    public HomePanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Main Content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 20, 20));

        totalSalesLabel = new JLabel("Total Sales: Loading...");
        dailyRevenueLabel = new JLabel("Revenue (Today): Loading...");
        inventoryCountLabel = new JLabel("Inventory Count: Loading...");
        customerCountLabel = new JLabel("Total Customers: Loading...");
        topSellingItemLabel = new JLabel("Top Selling Item: Loading...");
        activeEmployeesLabel = new JLabel("Active Employees: Loading...");

        mainPanel.add(totalSalesLabel);
        mainPanel.add(dailyRevenueLabel);
        mainPanel.add(inventoryCountLabel);
        mainPanel.add(customerCountLabel);
        mainPanel.add(topSellingItemLabel);
        mainPanel.add(activeEmployeesLabel);

        add(mainPanel, BorderLayout.CENTER);

        // Placeholder for charts - this can be updated to include JFreeChart or similar libraries
        JPanel chartsPanel = new JPanel();
        chartsPanel.setBorder(BorderFactory.createTitledBorder("Charts & Analytics"));
        chartsPanel.setPreferredSize(new Dimension(800, 400)); // Adjust size as needed
        chartsPanel.add(new JLabel("Sales Chart Placeholder")); // Placeholder for charts

        add(chartsPanel, BorderLayout.SOUTH);
    }

    // Set data for each section - these will be called from the main GUI to update the panel with real data
    public void setTotalSales(String totalSales) {
        totalSalesLabel.setText("Total Sales: " + totalSales);
    }

    public void setDailyRevenue(String dailyRevenue) {
        dailyRevenueLabel.setText("Revenue (Today): " + dailyRevenue);
    }

    public void setInventoryCount(String inventoryCount) {
        inventoryCountLabel.setText("Inventory Count: " + inventoryCount);
    }

    public void setCustomerCount(String customerCount) {
        customerCountLabel.setText("Total Customers: " + customerCount);
    }

    public void setTopSellingItem(String topSellingItem) {
        topSellingItemLabel.setText("Top Selling Item: " + topSellingItem);
    }

    public void setActiveEmployees(String activeEmployees) {
        activeEmployeesLabel.setText("Active Employees: " + activeEmployees);
    }

    public void addSalesChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(800, "Sales", "Mon");
        dataset.addValue(900, "Sales", "Tue");
        dataset.addValue(1200, "Sales", "Wed");
        dataset.addValue(1100, "Sales", "Thu");
        dataset.addValue(950, "Sales", "Fri");

        JFreeChart chart = ChartFactory.createLineChart(
                "Weekly Sales",
                "Days",
                "Amount",
                dataset
                );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(700, 400));
        add(chartPanel, BorderLayout.SOUTH);  // Add this to the panel
    }

}
