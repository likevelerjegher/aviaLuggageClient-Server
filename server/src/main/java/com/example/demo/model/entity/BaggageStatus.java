package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "baggage_status")
public class BaggageStatus extends AbstractEntity implements Serializable {
    @Column(name = "status_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusName statusName;

    public enum StatusName {
        REGISTRATION, SORTING_DEPARTURE, IN_TRANSIT, SORTING_ARRIVAL, ARRIVED, LOST
    }

    public StatusName getStatusName() {
        return statusName;
    }

    public void setStatusName(StatusName statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "BaggageStatus{id=" + getId() + ", statusName=" + statusName + "}";
    }
}