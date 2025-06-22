package com.example.ui;

import com.example.ui.panels.BaggagePanel;
import com.example.ui.panels.ClientPanel;
import com.example.ui.panels.FlightPanel;

import javax.swing.*;
import java.awt.*;

public class EmployeeFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public EmployeeFrame() {
        setTitle("Аэропорт — Сотрудник");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Навигационная панель
        JPanel navigationPanel = new JPanel(new GridLayout(1, 3));
        JButton clientsButton = new JButton("Клиенты");
        JButton baggageButton = new JButton("Багаж");
        JButton flightsButton = new JButton("Рейсы");

        clientsButton.addActionListener(e -> cardLayout.show(contentPanel, "Clients"));
        baggageButton.addActionListener(e -> cardLayout.show(contentPanel, "Baggage"));
        flightsButton.addActionListener(e -> cardLayout.show(contentPanel, "Flights"));

        navigationPanel.add(clientsButton);
        navigationPanel.add(baggageButton);
        navigationPanel.add(flightsButton);

        // Панели контента
        contentPanel.add(new ClientPanel(), "Clients");
        contentPanel.add(new BaggagePanel(), "Baggage");
        contentPanel.add(new FlightPanel(), "Flights");

        add(navigationPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
}