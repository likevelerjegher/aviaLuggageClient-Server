package com.example.controller;
import com.example.service.ApiClient;
import com.example.view.BaggageView;
import com.example.view.FlightView;
import com.example.view.PassengerView;
import com.example.view.ReportView;

public class MainController {
    private final ApiClient apiClient;

    public MainController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void handleBaggageView() {
        BaggageView view = new BaggageView(new BaggageController(apiClient));
        view.setVisible(true);
    }

    public void handleFlightView() {
        FlightView view = new FlightView(new FlightController(apiClient));
        view.setVisible(true);
    }

    public void handlePassengerView() {
        PassengerView view = new PassengerView(new PassengerController(apiClient));
        view.setVisible(true);
    }

    public void handleReportView() {
        ReportView view = new ReportView(new ReportController(apiClient));
        view.setVisible(true);
    }
}