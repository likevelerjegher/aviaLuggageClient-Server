package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FlightPanel extends JPanel {

    private JTable flightTable;
    private DefaultTableModel tableModel;
    private static final ObjectMapper mapper = new ObjectMapper();

    public FlightPanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Номер рейса", "Пункт назначения"}, 0);
        flightTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(flightTable);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton refreshButton = new JButton("Обновить");

        addButton.addActionListener(e -> addFlight());
        editButton.addActionListener(e -> editFlight());
        deleteButton.addActionListener(e -> deleteFlight());
        refreshButton.addActionListener(e -> loadFlights());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadFlights();
    }

    private void loadFlights() {
        try {
            String response = ApiClient.get("/flights");
            List<Flight> flights = mapper.readValue(response, new TypeReference<List<Flight>>() {});

            tableModel.setRowCount(0);
            for (Flight f : flights) {
                tableModel.addRow(new Object[]{f.getId(), f.getFlightNumber(), f.getDestination()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки рейсов: " + e.getMessage());
        }
    }

    private void addFlight() {
        FlightForm form = new FlightForm(null);
        form.setModal(true);
        form.setVisible(true);
        if (form.isSaved()) {
            loadFlights();
        }
    }

    private void editFlight() {
        int selectedRow = flightTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите рейс для редактирования");
            return;
        }

        Flight flight = new Flight();
        flight.setId((Long) tableModel.getValueAt(selectedRow, 0));
        flight.setFlightNumber(tableModel.getValueAt(selectedRow, 1).toString());
        flight.setDestination(tableModel.getValueAt(selectedRow, 2).toString());

        FlightForm form = new FlightForm(flight);
        form.setModal(true);
        form.setVisible(true);
        if (form.isSaved()) {
            loadFlights();
        }
    }

    private void deleteFlight() {
        int selectedRow = flightTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите рейс для удаления");
            return;
        }

        Long flightId = (Long) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Удалить рейс?", "Подтвердите", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            ApiClient.delete("/flights/" + flightId);
            loadFlights();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления рейса: " + e.getMessage());
        }
    }
}