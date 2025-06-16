package com.example.view;

import com.example.controller.BaggageController;
import com.example.model.Baggage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BaggageView extends JFrame {
    private JTable baggageTable;
    private JTextField passengerIdField;
    private JTextField flightIdField;
    private JTextField weightField;
    private JTextField dimensionsField;
    private JTextField typeField;
    private JComboBox<String> statusCombo;

    public BaggageView(BaggageController controller) {
        setTitle("Baggage Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        baggageTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Passenger ID", "Flight ID", "Weight", "Dimensions", "Type", "Status"}, 0));
        JScrollPane scrollPane = new JScrollPane(baggageTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Passenger ID:"));
        passengerIdField = new JTextField();
        inputPanel.add(passengerIdField);

        inputPanel.add(new JLabel("Flight ID:"));
        flightIdField = new JTextField();
        inputPanel.add(flightIdField);

        inputPanel.add(new JLabel("Weight:"));
        weightField = new JTextField();
        inputPanel.add(weightField);

        inputPanel.add(new JLabel("Dimensions:"));
        dimensionsField = new JTextField();
        inputPanel.add(dimensionsField);

        inputPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        inputPanel.add(typeField);

        inputPanel.add(new JLabel("Status:"));
        statusCombo = new JComboBox<>(new String[]{"REGISTRATION", "SORTING_DEPARTURE", "IN_TRANSIT", "SORTING_ARRIVAL", "ARRIVED", "LOST"});
        inputPanel.add(statusCombo);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("Create Baggage");
        createButton.addActionListener(e -> controller.handleCreateBaggage(
                passengerIdField.getText(), flightIdField.getText(), weightField.getText(),
                dimensionsField.getText(), typeField.getText(), (String) statusCombo.getSelectedItem()));
        buttonPanel.add(createButton);

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(e -> controller.handleUpdateStatus(
                baggageTable.getSelectedRow(), (String) statusCombo.getSelectedItem()));
        buttonPanel.add(updateStatusButton);

        JButton searchButton = new JButton("Search by Passenger");
        searchButton.addActionListener(e -> controller.handleSearchByPassenger(passengerIdField.getText()));
        buttonPanel.add(searchButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public void updateBaggageTable(List<Baggage> baggageList) {
        DefaultTableModel model = (DefaultTableModel) baggageTable.getModel();
        model.setRowCount(0);
        for (Baggage baggage : baggageList) {
            model.addRow(new Object[]{
                    baggage.getId(), baggage.getPassengerId(), baggage.getFlightId(),
                    baggage.getWeight(), baggage.getDimensions(), baggage.getType(), baggage.getStatus()
            });
        }
    }

    public JTable getBaggageTable() {
        return baggageTable;
    }
}