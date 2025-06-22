package com.example.controller;

import com.example.model.Passenger;
import com.example.service.ApiClient;
import com.example.view.PassengerView;
import com.example.view.LoginView;

import javax.swing.*;
import java.util.logging.Logger;

public class PassengerController {
    private static final Logger logger = Logger.getLogger(PassengerController.class.getName());
    private final ApiClient apiClient;
    private final PassengerView view;

    public PassengerController(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.view = new PassengerView(this);
        loadPassengers();
        view.setVisible(true);
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
            JOptionPane.showMessageDialog(view, "Passenger created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            logger.severe("Error creating passenger: " + e.getMessage());
            handleError(e);
        }
    }

    private void loadPassengers() {
        logger.info("Loading passengers, token: " + (apiClient.getToken() != null ? "Present" : "Null"));
        if (apiClient.getToken() == null || apiClient.getToken().isEmpty()) {
            logger.warning("No valid token, redirecting to login");
            redirectToLogin();
            return;
        }

        try {
            view.updatePassengerTable(apiClient.getAllPassengers());
            logger.info("Passengers loaded successfully");
        } catch (Exception e) {
            logger.severe("Error loading passengers: " + e.getMessage());
            handleError(e);
        }
    }

    private void handleError(Exception e) {
        if (e.getMessage().contains("Session expired")) {
            JOptionPane.showMessageDialog(view, "Session expired, please log in again", "Error", JOptionPane.ERROR_MESSAGE);
            redirectToLogin();
        } else {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void redirectToLogin() {
        view.dispose();
        LoginView loginView = new LoginView(new LoginController(apiClient));
        loginView.setVisible(true);
    }
}