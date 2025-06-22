package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Baggage;
import com.example.model.BaggageStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BaggagePanel extends JPanel {

    private JTable baggageTable;
    private DefaultTableModel tableModel;
    private static final ObjectMapper mapper = new ObjectMapper();

    public BaggagePanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Длина", "Ширина", "Высота", "Вес", "Статус"}, 0);
        baggageTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(baggageTable);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton refreshButton = new JButton("Обновить");

        addButton.addActionListener(e -> addBaggage());
        editButton.addActionListener(e -> editBaggage());
        deleteButton.addActionListener(e -> deleteBaggage());
        refreshButton.addActionListener(e -> loadBaggage());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadBaggage();
    }

    private void loadBaggage() {
        try {
            String response = ApiClient.get("/baggage");
            List<Baggage> baggageList = mapper.readValue(response, new TypeReference<List<Baggage>>() {});

            tableModel.setRowCount(0);
            for (Baggage b : baggageList) {
                tableModel.addRow(new Object[]{b.getId(), b.getLength(), b.getWidth(), b.getHeight(), b.getWeight(), b.getStatus()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки багажа: " + e.getMessage());
        }
    }

    private void addBaggage() {
        BaggageForm form = new BaggageForm(null);
        form.setModal(true);
        form.setVisible(true);
        if (form.isSaved()) {
            loadBaggage();
        }
    }

    private void editBaggage() {
        int selectedRow = baggageTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите багаж для редактирования");
            return;
        }

        Baggage baggage = new Baggage();
        baggage.setId((Long) tableModel.getValueAt(selectedRow, 0));
        baggage.setLength(Double.parseDouble(tableModel.getValueAt(selectedRow, 1).toString()));
        baggage.setWidth(Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString()));
        baggage.setHeight(Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString()));
        baggage.setWeight(Double.parseDouble(tableModel.getValueAt(selectedRow, 4).toString()));

        String statusStr = tableModel.getValueAt(selectedRow, 5).toString();
        baggage.setStatus(BaggageStatus.valueOf(statusStr));

        BaggageForm form = new BaggageForm(baggage);
        form.setModal(true);
        form.setVisible(true);
        if (form.isSaved()) {
            loadBaggage();
        }
    }

    private void deleteBaggage() {
        int selectedRow = baggageTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите багаж для удаления");
            return;
        }

        Long baggageId = (Long) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Удалить багаж?", "Подтвердите", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            ApiClient.delete("/baggage/" + baggageId);
            loadBaggage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления багажа: " + e.getMessage());
        }
    }

}