package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Client;
import com.example.model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;

public class ClientForm extends JDialog {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField passportField;
    private JTextField flightIdField;
    private boolean saved = false;
    private Client client;

    public ClientForm(Client client) {
        this.client = client;
        setTitle(client == null ? "Добавить клиента" : "Редактировать клиента");
        setSize(300, 250);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Имя:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Фамилия:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Паспорт:"));
        passportField = new JTextField();
        add(passportField);

        add(new JLabel("ID рейса:"));
        flightIdField = new JTextField();
        add(flightIdField);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveClient());
        add(saveButton);

        if (client != null) {
            firstNameField.setText(client.getFirstName());
            lastNameField.setText(client.getLastName());
            passportField.setText(client.getPassportNumber());

            if (client.getFlight() != null && client.getFlight().getId() != null) {
                flightIdField.setText(client.getFlight().getId().toString());
            } else {
                flightIdField.setText("");
            }
        }
    }

    private void saveClient() {
        try {
            Client newClient = new Client();
            newClient.setFirstName(firstNameField.getText());
            newClient.setLastName(lastNameField.getText());
            newClient.setPassportNumber(passportField.getText());

            Flight flight = new Flight();
            flight.setId(Long.parseLong(flightIdField.getText()));
            newClient.setFlight(flight);

            if (client == null) {
                ApiClient.post("/clients", newClient);
            } else {
                newClient.setId(client.getId());
                ApiClient.put("/clients/" + client.getId(), newClient);
            }

            saved = true;
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + e.getMessage());
        }
    }


    public boolean isSaved() {
        return saved;
    }
}