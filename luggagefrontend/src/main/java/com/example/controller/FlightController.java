package com.example.controller;

import com.example.model.Flight;
import com.example.service.ApiClient;
import com.example.view.FlightView;

import javax.swing.*;
import java.time.LocalDateTime;

public class FlightController {
    private final ApiClient apiClient;
    private final FlightView view;

    public FlightController(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.view = new FlightView(this);
        loadFlights();
        view.setVisible(true); // Ensure the view is visible
    }

    public void handleCreateFlight(String flightNumber, String destination) {
        try {
            Flight flight = new Flight();
            flight.setFlightNumber(flightNumber);
            flight.setDestination(destination);
            flight.setDepartureTime(LocalDateTime.now());
            flight.setArrivalTime(LocalDateTime.now().plusHours(2));
            apiClient.createFlight(flight);
            loadFlights();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFlights() {
        try {
            view.updateFlightTable(apiClient.getAllFlights());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}