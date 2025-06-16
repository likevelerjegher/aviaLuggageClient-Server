package com.example.view;

import com.example.controller.MainController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public MainView(MainController controller) {
        setTitle("Baggage Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton baggageButton = new JButton("Manage Baggage");
        baggageButton.addActionListener(e -> controller.handleBaggageView());
        panel.add(baggageButton);

        JButton flightButton = new JButton("Manage Flights");
        flightButton.addActionListener(e -> controller.handleFlightView());
        panel.add(flightButton);

        JButton passengerButton = new JButton("Manage Passengers");
        passengerButton.addActionListener(e -> controller.handlePassengerView());
        panel.add(passengerButton);

        JButton reportButton = new JButton("View Reports");
        reportButton.addActionListener(e -> controller.handleReportView());
        panel.add(reportButton);

        add(panel);
    }
}