package com.example.view;

import com.example.controller.PassengerController;
import com.example.model.Passenger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PassengerView extends JFrame {
    private JTable passengerTable;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField passportNumberField;
    private JTextField ticketNumberField;

    public PassengerView(PassengerController controller) {
        setTitle("Passenger Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        passengerTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "First Name", "Last Name", "Passport Number", "Ticket Number"}, 0));
        JScrollPane scrollPane = new JScrollPane(passengerTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("Passport Number:"));
        passportNumberField = new JTextField();
        inputPanel.add(passportNumberField);

        inputPanel.add(new JLabel("Ticket Number:"));
        ticketNumberField = new JTextField();
        inputPanel.add(ticketNumberField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("Create Passenger");
        createButton.addActionListener(e -> controller.handleCreatePassenger(
                firstNameField.getText(), lastNameField.getText(),
                passportNumberField.getText(), ticketNumberField.getText()));
        buttonPanel.add(createButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public void updatePassengerTable(List<Passenger> passengerList) {
        DefaultTableModel model = (DefaultTableModel) passengerTable.getModel();
        model.setRowCount(0);
        for (Passenger passenger : passengerList) {
            model.addRow(new Object[]{
                    passenger.getId(), passenger.getFirstName(), passenger.getLastName(),
                    passenger.getPassportNumber(), passenger.getTicketNumber()
            });
        }
    }
}