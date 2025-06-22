package com.example.ui;

import com.example.api.ApiClient;
import com.example.model.Client;
import com.example.util.TokenStorage;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {

    private JTextArea infoArea;
    private static final ObjectMapper mapper = new ObjectMapper();

    public ClientFrame() {
        setTitle("Аэропорт — Клиент");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);

        JButton refreshButton = new JButton("Обновить");
        refreshButton.addActionListener(e -> loadClientData());

        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        loadClientData();
    }

    private void loadClientData() {
        try {
            String response = ApiClient.get("/clients/me");
            Client client = mapper.readValue(response, Client.class);

            StringBuilder sb = new StringBuilder();
            sb.append("Имя: ").append(client.getFirstName()).append("\n");
            sb.append("Фамилия: ").append(client.getLastName()).append("\n");
            sb.append("Паспорт: ").append(client.getPassportNumber()).append("\n");
            sb.append("Номер билета: ").append(client.getTicketNumber()).append("\n");
            sb.append("ID рейса: ").append(client.getFlight() != null ? client.getFlight().getId() : "нет").append("\n");
            sb.append("ID багажа: ").append(client.getBaggage() != null ? client.getBaggage().getId() : "нет").append("\n");

            if (client.getBaggage() != null && client.getBaggage().getId() != null) {
                String baggageResponse = ApiClient.get("/baggage/" + client.getBaggage().getId());
                sb.append("\nИнформация о багаже:\n").append(baggageResponse);
            }

            infoArea.setText(sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage());
        }
    }

}