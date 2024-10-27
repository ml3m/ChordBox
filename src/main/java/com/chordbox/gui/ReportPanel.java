package com.chordbox.gui;

import com.chordbox.services.ReportService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportPanel extends JPanel {
    private ReportService reportService;
    private JTextArea reportArea;

    public ReportPanel() {
        this.reportService = new ReportService();
        setLayout(new BorderLayout());

        // Report display area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);

        // Generate report button
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String report = reportService.generateSalesReport();
                reportArea.setText(report);
            }
        });

        add(generateReportButton, BorderLayout.SOUTH);
    }
}
