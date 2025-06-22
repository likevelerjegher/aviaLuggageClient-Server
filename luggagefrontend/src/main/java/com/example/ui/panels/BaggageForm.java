package com.example.ui.panels;

import com.example.api.ApiClient;
import com.example.model.Baggage;
import com.example.model.BaggageStatus;

import javax.swing.*;
import java.awt.*;

public class BaggageForm extends JDialog {

    private JTextField lengthField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField weightField;
    private JComboBox<BaggageStatus> statusComboBox;

    private boolean saved = false;
    private Baggage baggage;

    public BaggageForm(Baggage baggage) {
        this.baggage = baggage;
        setTitle(baggage == null ? "Добавить багаж" : "Редактировать багаж");
        setSize(300, 300);
        setLayout(new GridLayout(6, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Длина:"));
        lengthField = new JTextField();
        add(lengthField);

        add(new JLabel("Ширина:"));
        widthField = new JTextField();
        add(widthField);

        add(new JLabel("Высота:"));
        heightField = new JTextField();
        add(heightField);

        add(new JLabel("Вес:"));
        weightField = new JTextField();
        add(weightField);

        add(new JLabel("Статус:"));
        statusComboBox = new JComboBox<>(BaggageStatus.values());
        add(statusComboBox);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveBaggage());
        add(saveButton);

        if (baggage != null) {
            lengthField.setText(String.valueOf(baggage.getLength()));
            widthField.setText(String.valueOf(baggage.getWidth()));
            heightField.setText(String.valueOf(baggage.getHeight()));
            weightField.setText(String.valueOf(baggage.getWeight()));
            statusComboBox.setSelectedItem(baggage.getStatus());
        }
    }

    private void saveBaggage() {
        try {
            Baggage newBaggage = new Baggage();
            newBaggage.setLength(Double.parseDouble(lengthField.getText()));
            newBaggage.setWidth(Double.parseDouble(widthField.getText()));
            newBaggage.setHeight(Double.parseDouble(heightField.getText()));
            newBaggage.setWeight(Double.parseDouble(weightField.getText()));
            newBaggage.setStatus((BaggageStatus) statusComboBox.getSelectedItem());

            if (baggage == null) {
                ApiClient.post("/baggage", newBaggage);
            } else {
                newBaggage.setId(baggage.getId());
                ApiClient.put("/baggage/" + baggage.getId(), newBaggage);
            }

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
