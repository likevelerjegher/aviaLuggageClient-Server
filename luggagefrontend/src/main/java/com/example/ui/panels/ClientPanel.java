package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Client;
import com.example.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientPanel extends JPanel {

    private JTable clientTable;
    private DefaultTableModel tableModel;
    private static final ObjectMapper mapper = new ObjectMapper();
    private Map<Long, Flight> flightsMap = new HashMap<>();


    public ClientPanel() {
        setLayout(new BorderLayout());

        // Таблица
        tableModel = new DefaultTableModel(new String[]{"ID", "Имя", "Фамилия", "Паспорт", "Рейс"}, 0);
        clientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clientTable);

        // Кнопки
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton refreshButton = new JButton("Обновить");

        addButton.addActionListener(e -> addClient());
        editButton.addActionListener(e -> editClient());
        deleteButton.addActionListener(e -> deleteClient());
        refreshButton.addActionListener(e -> loadClients());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadClients();
    }

    private void loadClients() {
        try {
            loadFlights(); // Загрузить рейсы перед клиентами

            String response = ApiClient.get("/clients");
            List<Client> clients = mapper.readValue(response, new TypeReference<List<Client>>() {});

            tableModel.setRowCount(0);
            for (Client c : clients) {
                String flightNumber = "";
                if (c.getFlight() != null && c.getFlight().getId() != null) {
                    Flight flight = flightsMap.get(c.getFlight().getId());
                    if (flight != null) {
                        flightNumber = flight.getFlightNumber();
                    }
                }
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getPassportNumber(),
                        flightNumber
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки клиентов: " + e.getMessage());
        }
    }


    private void addClient() {
        ClientForm form = new ClientForm(null);
        form.setVisible(true);
        if (form.isSaved()) {
            loadClients();
        }
    }

    private void editClient() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите клиента для редактирования");
            return;
        }

        Client client = new Client();
        client.setId((Long) tableModel.getValueAt(selectedRow, 0));
        client.setFirstName((String) tableModel.getValueAt(selectedRow, 1));
        client.setLastName((String) tableModel.getValueAt(selectedRow, 2));
        client.setPassportNumber((String) tableModel.getValueAt(selectedRow, 3));

        String flightNumber = (String) tableModel.getValueAt(selectedRow, 4);

        Flight flight = null;
        for (Flight f : flightsMap.values()) {
            if (f.getFlightNumber().equals(flightNumber)) {
                flight = f;
                break;
            }
        }

        if (flight != null) {
            client.setFlight(flight);
        }

        ClientForm form = new ClientForm(client);
        form.setVisible(true);
        if (form.isSaved()) {
            loadClients();
        }
    }



    private void deleteClient() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите клиента для удаления");
            return;
        }

        Long clientId = (Long) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить клиента?", "Подтвердите удаление", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            ApiClient.delete("/clients/" + clientId);
            loadClients();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления клиента: " + e.getMessage());
        }
    }
    private void loadFlights() {
        try {
            String response = ApiClient.get("/flights");
            List<Flight> flights = mapper.readValue(response, new TypeReference<List<Flight>>() {});
            flightsMap.clear();
            for (Flight f : flights) {
                flightsMap.put(f.getId(), f);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки рейсов: " + e.getMessage());
        }
    }


}
