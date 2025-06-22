package com.example.controller;

import com.example.model.UserDTO;
import com.example.service.ApiClient;
import com.example.view.EmployeeView;

import javax.swing.*;

public class EmployeeController {
    private final ApiClient apiClient;
    private final EmployeeView view;

    public EmployeeController(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.view = new EmployeeView(this);
        loadEmployees();
        view.setVisible(true);
    }

    public void handleCreateEmployee(UserDTO user) {
        try {
            apiClient.createEmployee(user);
            loadEmployees();
            JOptionPane.showMessageDialog(view, "Employee created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteEmployee(Long id) {
        try {
            apiClient.deleteEmployee(id);
            loadEmployees();
            JOptionPane.showMessageDialog(view, "Employee deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleUpdateEmployee(Long id, UserDTO user) {
        try {
            apiClient.updateEmployee(id, user);
            loadEmployees();
            JOptionPane.showMessageDialog(view, "Employee updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployees() {
        try {
            view.updateEmployeeTable(apiClient.getAllEmployees());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}