package com.example.ui;

import com.example.ui.panels.BaggagePanel;
import com.example.ui.panels.ClientPanel;
import com.example.ui.panels.EmployeePanel;
import com.example.ui.panels.FlightPanel;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public AdminFrame() {
        setTitle("Аэропорт — Администратор");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Навигационная панель
        JPanel navigationPanel = new JPanel(new GridLayout(1, 4));
        JButton clientsButton = new JButton("Клиенты");
        JButton baggageButton = new JButton("Багаж");
        JButton flightsButton = new JButton("Рейсы");
        JButton employeesButton = new JButton("Сотрудники");

        clientsButton.addActionListener(e -> cardLayout.show(contentPanel, "Clients"));
        baggageButton.addActionListener(e -> cardLayout.show(contentPanel, "Baggage"));
        flightsButton.addActionListener(e -> cardLayout.show(contentPanel, "Flights"));
        employeesButton.addActionListener(e -> cardLayout.show(contentPanel, "Employees"));

        navigationPanel.add(clientsButton);
        navigationPanel.add(baggageButton);
        navigationPanel.add(flightsButton);
        navigationPanel.add(employeesButton);

        // Панели контента
        contentPanel.add(new ClientPanel(), "Clients");
        contentPanel.add(new BaggagePanel(), "Baggage");
        contentPanel.add(new FlightPanel(), "Flights");
        contentPanel.add(new EmployeePanel(), "Employees");

        add(navigationPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
}
