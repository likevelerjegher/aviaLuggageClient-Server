package com.example.view;

import com.example.controller.ReportController;
import com.example.model.Baggage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportView extends JFrame {
    private JTable lostBaggageTable;
    private JTextArea statisticsArea;

    public ReportView(ReportController controller) {
        setTitle("Reports");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lost Baggage Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JLabel("Lost Baggage Report"), BorderLayout.NORTH);
        lostBaggageTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Passenger ID", "Flight ID", "Weight", "Dimensions", "Type", "Status"}, 0));
        JScrollPane tableScrollPane = new JScrollPane(lostBaggageTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Statistics Area
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.add(new JLabel("Baggage Statistics"), BorderLayout.NORTH);
        statisticsArea = new JTextArea(5, 20);
        statisticsArea.setEditable(false);
        JScrollPane statsScrollPane = new JScrollPane(statisticsArea);
        statsPanel.add(statsScrollPane, BorderLayout.CENTER);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, statsPanel);
        splitPane.setDividerLocation(300);
        panel.add(splitPane, BorderLayout.CENTER);

        add(panel);
    }

    public void updateLostBaggageTable(List<Baggage> baggageList) {
        DefaultTableModel model = (DefaultTableModel) lostBaggageTable.getModel();
        model.setRowCount(0);
        for (Baggage baggage : baggageList) {
            model.addRow(new Object[]{
                    baggage.getId(), baggage.getPassengerId(), baggage.getFlightId(),
                    baggage.getWeight(), baggage.getDimensions(), baggage.getType(), baggage.getStatus()
            });
        }
    }

    public void updateStatistics(String stats) {
        statisticsArea.setText(stats);
    }
}