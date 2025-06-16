package com.example.controller;

import com.example.service.ApiClient;
import com.example.view.ReportView;

import javax.swing.*;

public class ReportController {
    private final ApiClient apiClient;
    private final ReportView view;

    public ReportController(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.view = new ReportView(this);
        loadReports();
        view.setVisible(true); // Ensure the view is visible
    }

    private void loadReports() {
        try {
            view.updateLostBaggageTable(apiClient.getLostBaggageReport());
            view.updateStatistics(apiClient.getBaggageStatistics());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}