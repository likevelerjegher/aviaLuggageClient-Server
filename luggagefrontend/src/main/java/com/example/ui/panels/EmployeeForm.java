package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Employee;

import javax.swing.*;
import java.awt.*;

public class EmployeeForm extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean saved = false;

    public EmployeeForm() {
        setTitle("Добавить сотрудника");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Логин:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Пароль:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveEmployee());
        add(saveButton);
    }

    private void saveEmployee() {
        try {
            Employee newEmployee = new Employee();
            newEmployee.setUsername(usernameField.getText());
            newEmployee.setPassword(new String(passwordField.getPassword()));

            ApiClient.post("/employees", newEmployee);

            saved = true;
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + e.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }
}