package com.example.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "luggage_status_history")
public class LuggageStatusHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "luggage_id", nullable = false)
    private AbstractLuggage luggage;

    @Column(name = "status")
    private String status;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public LuggageStatusHistory() {}

    public LuggageStatusHistory(AbstractLuggage luggage, String status, LocalDateTime timestamp, Employee employee) {
        this.luggage = luggage;
        this.status = status;
        this.timestamp = timestamp;
        this.employee = employee;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public AbstractLuggage getLuggage() {
        return luggage;
    }

    public void setLuggage(AbstractLuggage luggage) {
        this.luggage = luggage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}