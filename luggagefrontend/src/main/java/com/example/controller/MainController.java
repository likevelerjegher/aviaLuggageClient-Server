package com.example.controller;

import com.example.service.ApiClient;
import com.example.view.*;

import javax.swing.*;
import java.util.logging.Logger;

public class MainController {
    private static final Logger logger = Logger.getLogger(MainController.class.getName());
    private final ApiClient apiClient;

    public MainController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void handleBaggageView() {
        logger.info("Opening Baggage View");
        new BaggageView(new BaggageController(apiClient)).setVisible(true);
    }

    public void handleFlightView() {
        logger.info("Opening Flight View");
        new FlightView(new FlightController(apiClient)).setVisible(true);
    }

    public void handlePassengerView() {
        logger.info("Opening Passenger View, token: " + (apiClient.getToken() != null ? "Present" : "Null"));
        if (apiClient.getToken() == null || apiClient.getToken().isEmpty()) {
            logger.warning("No valid token, redirecting to login");
            JOptionPane.showMessageDialog(null, "Please log in as admin", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new PassengerView(new PassengerController(apiClient)).setVisible(true);
    }

    public void handleReportView() {
        logger.info("Opening Report View");
        new ReportView(new ReportController(apiClient)).setVisible(true);
    }

    public void handleEmployeeView() {
        logger.info("Opening Employee View");
        new EmployeeView(new EmployeeController(apiClient)).setVisible(true);
    }
}