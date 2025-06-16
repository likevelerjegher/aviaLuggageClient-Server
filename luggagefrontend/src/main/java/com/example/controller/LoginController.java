package com.example.controller;

import com.example.service.ApiClient;
import com.example.view.MainView;

import javax.swing.*;

public class LoginController {
    private final ApiClient apiClient;

    public LoginController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void handleLogin(String username, String password) {
        try {
            apiClient.login(username, password);
            MainView mainView = new MainView(new MainController(apiClient));
            mainView.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}