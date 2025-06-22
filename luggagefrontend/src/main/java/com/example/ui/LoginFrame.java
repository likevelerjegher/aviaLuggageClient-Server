package com.example.ui;

import com.example.api.AuthService;
import com.example.model.AuthResponse;
import com.example.util.TokenStorage;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Аэропорт — Вход");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JLabel usernameLabel = new JLabel("Логин:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Войти");
        loginButton.addActionListener(e -> login());

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            AuthResponse response = AuthService.login(username, password);
            TokenStorage.setToken(response.getToken());

            // Открываем окно по роли
            if (response.getRole().equals("ADMIN")) {
                new AdminFrame().setVisible(true);
            } else if (response.getRole().equals("EMPLOYEE")) {
                new EmployeeFrame().setVisible(true);
            } else if (response.getRole().equals("CLIENT")) {
                new ClientFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Неизвестная роль: " + response.getRole());
                return;
            }

            // Закрываем окно логина
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ошибка входа: " + ex.getMessage());
        }
    }
}
