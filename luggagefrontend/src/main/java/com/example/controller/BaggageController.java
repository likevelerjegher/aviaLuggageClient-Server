package com.example.controller;

import com.example.model.Baggage;
import com.example.service.ApiClient;
import com.example.view.BaggageView;

import javax.swing.JOptionPane;

public class BaggageController {
    private final ApiClient apiClient;
    private final BaggageView view;

    public BaggageController(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.view = new BaggageView(this);
        loadBaggage();
    }

    public void handleCreateBaggage(String passengerId, String flightId, String weight, String dimensions, String type, String status) {
        try {
            Baggage baggage = new Baggage();
            baggage.setPassengerId(Long.parseLong(passengerId));
            baggage.setFlightId(Long.parseLong(flightId));
            baggage.setWeight(Double.parseDouble(weight));
            baggage.setDimensions(dimensions);
            baggage.setType(type);
            baggage.setStatus(status);
            apiClient.createBaggage(baggage);
            loadBaggage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleUpdateStatus(int rowIndex, String status) {
        try {
            if (rowIndex >= 0) {
                Long id = (Long) view.getBaggageTable().getModel().getValueAt(rowIndex, 0);
                apiClient.updateBaggageStatus(id, status);
                loadBaggage();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleSearchByPassenger(String passengerId) {
        try {
            view.updateBaggageTable(apiClient.getBaggageByPassenger(Long.parseLong(passengerId)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBaggage() {
        try {
            view.updateBaggageTable(apiClient.getAllBaggage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}