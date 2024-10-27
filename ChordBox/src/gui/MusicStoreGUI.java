package gui;

import javax.swing.*;
import java.awt.*;

public class MusicStoreGUI extends JFrame {
    public MusicStoreGUI() {
        setTitle("Music Store Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabbed Pane for different management functionalities
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Inventory", new InventoryPanel());
        tabbedPane.addTab("Orders", new OrderPanel());
        tabbedPane.addTab("Customers", new CustomerPanel());
        tabbedPane.addTab("Reports", new ReportPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MusicStoreGUI gui = new MusicStoreGUI();
            gui.setVisible(true);
        });
    }
}
