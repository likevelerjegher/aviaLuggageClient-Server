package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeePanel extends JPanel {

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private static final ObjectMapper mapper = new ObjectMapper();

    public EmployeePanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Логин"}, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить");
        JButton deleteButton = new JButton("Удалить");
        JButton refreshButton = new JButton("Обновить");

        addButton.addActionListener(e -> addEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        refreshButton.addActionListener(e -> loadEmployees());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadEmployees();
    }

    private void loadEmployees() {
        try {
            String response = ApiClient.get("/employees");
            List<Employee> employees = mapper.readValue(response, new TypeReference<List<Employee>>() {});

            tableModel.setRowCount(0);
            for (Employee e : employees) {
                tableModel.addRow(new Object[]{e.getId(), e.getUsername()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки сотрудников: " + e.getMessage());
        }
    }

    private void addEmployee() {
        EmployeeForm form = new EmployeeForm();
        form.setModal(true);
        form.setVisible(true);
        if (form.isSaved()) {
            loadEmployees();
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите сотрудника для удаления");
            return;
        }

        Long employeeId = (Long) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Удалить сотрудника?", "Подтвердите", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            ApiClient.delete("/employees/" + employeeId);
            loadEmployees();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления сотрудника: " + e.getMessage());
        }
    }
}