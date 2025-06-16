package com.example.controller;

import com.example.model.Passenger;
import com.example.service.ApiClient;
import com.example.view.PassengerView;

import javax.swing.*;

public class PassengerController {
    private final ApiClient apiClient;
    private final PassengerView view;

    public PassengerController(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.view = new PassengerView(this);
        loadPassengers();
        view.setVisible(true); // Устанавливаем видимость окна
    }

    public void handleCreatePassenger(String firstName, String lastName, String passportNumber, String ticketNumber) {
        try {
            Passenger passenger = new Passenger();
            passenger.setFirstName(firstName);
            passenger.setLastName(lastName);
            passenger.setPassportNumber(passportNumber);
            passenger.setTicketNumber(ticketNumber);
            apiClient.createPassenger(passenger);
            loadPassengers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPassengers() {
        try {
            view.updatePassengerTable(apiClient.getAllPassengers());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}