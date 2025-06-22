package com.example.view;


import com.example.controller.EmployeeController;
import com.example.model.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeView extends JFrame {
    private JTable employeeTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createButton, deleteButton, updateButton;

    public EmployeeView(EmployeeController controller) {
        setTitle("Employee Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        employeeTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Username", "Role"}, 0));
        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    usernameField.setText((String) employeeTable.getValueAt(selectedRow, 1));
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createButton = new JButton("Create Employee");
        createButton.addActionListener(e -> {
            UserDTO user = new UserDTO();
            user.setUsername(usernameField.getText());
            user.setPassword(new String(passwordField.getPassword()));
            user.setRole("EMPLOYEE");
            controller.handleCreateEmployee(user);
            clearFields();
        });
        buttonPanel.add(createButton);

        deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                Long id = (Long) employeeTable.getValueAt(selectedRow, 0);
                controller.handleDeleteEmployee(id);
            } else {
                JOptionPane.showMessageDialog(this, "Select an employee to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(deleteButton);

        updateButton = new JButton("Update Employee");
        updateButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                Long id = (Long) employeeTable.getValueAt(selectedRow, 0);
                UserDTO user = new UserDTO();
                user.setId(id);
                user.setUsername(usernameField.getText());
                String password = new String(passwordField.getPassword());
                if (!password.isEmpty()) {
                    user.setPassword(password);
                }
                user.setRole("EMPLOYEE");
                controller.handleUpdateEmployee(id, user);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Select an employee to update", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(updateButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public void updateEmployeeTable(List<UserDTO> employeeList) {
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);
        for (UserDTO employee : employeeList) {
            model.addRow(new Object[]{employee.getId(), employee.getUsername(), employee.getRole()});
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}