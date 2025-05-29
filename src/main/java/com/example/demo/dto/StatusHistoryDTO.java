package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StatusHistoryDTO implements Serializable {
    private Long historyId;
    private Long luggageId;
    private String status;
    private LocalDateTime timestamp;
    private Long employeeId;

    public StatusHistoryDTO() {}

    public StatusHistoryDTO(Long historyId, Long luggageId, String status, LocalDateTime timestamp, Long employeeId) {
        this.historyId = historyId;
        this.luggageId = luggageId;
        this.status = status;
        this.timestamp = timestamp;
        this.employeeId = employeeId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getLuggageId() {
        return luggageId;
    }

    public void setLuggageId(Long luggageId) {
        this.luggageId = luggageId;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
