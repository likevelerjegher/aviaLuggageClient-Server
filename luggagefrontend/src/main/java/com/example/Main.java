package com.example;

import com.example.controller.LoginController;
import com.example.service.ApiClient;
import com.example.view.LoginView;

public class Main {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient("http://localhost:8080");
        LoginController controller = new LoginController(apiClient);
        LoginView loginView = new LoginView(controller);
        loginView.setVisible(true);
    }
}