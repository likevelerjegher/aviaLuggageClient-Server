package com.example.view;

import com.example.controller.FlightController;
import com.example.model.Flight;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FlightView extends JFrame {
    private JTable flightTable;
    private JTextField flightNumberField;
    private JTextField destinationField;

    public FlightView(FlightController controller) {
        setTitle("Flight Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        flightTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Flight Number", "Departure Time", "Arrival Time", "Destination"}, 0));
        JScrollPane scrollPane = new JScrollPane(flightTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Flight Number:"));
        flightNumberField = new JTextField();
        inputPanel.add(flightNumberField);

        inputPanel.add(new JLabel("Destination:"));
        destinationField = new JTextField();
        inputPanel.add(destinationField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("Create Flight");
        createButton.addActionListener(e -> controller.handleCreateFlight(
                flightNumberField.getText(), destinationField.getText()));
        buttonPanel.add(createButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public void updateFlightTable(List<Flight> flightList) {
        DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
        model.setRowCount(0);
        for (Flight flight : flightList) {
            model.addRow(new Object[]{
                    flight.getId(), flight.getFlightNumber(), flight.getDepartureTime(),
                    flight.getArrivalTime(), flight.getDestination()
            });
        }
    }
}