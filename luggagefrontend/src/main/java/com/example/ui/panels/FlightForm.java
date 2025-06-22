package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;

public class FlightForm extends JDialog {

    private JTextField flightNumberField;
    private JTextField destinationField;
    private boolean saved = false;
    private Flight flight;

    public FlightForm(Flight flight) {
        this.flight = flight;
        setTitle(flight == null ? "Добавить рейс" : "Редактировать рейс");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Номер рейса:"));
        flightNumberField = new JTextField();
        add(flightNumberField);

        add(new JLabel("Пункт назначения:"));
        destinationField = new JTextField();
        add(destinationField);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveFlight());
        add(saveButton);

        if (flight != null) {
            flightNumberField.setText(flight.getFlightNumber());
            destinationField.setText(flight.getDestination());
        }
    }

    private void saveFlight() {
        try {
            Flight newFlight = new Flight();
            newFlight.setFlightNumber(flightNumberField.getText());
            newFlight.setDestination(destinationField.getText());

            if (flight == null) {
                ApiClient.post("/flights", newFlight);
            } else {
                newFlight.setId(flight.getId());
                ApiClient.put("/flights/" + flight.getId(), newFlight);
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
