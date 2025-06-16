package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "handling_records")
public class HandlingRecord extends AbstractEntity implements Serializable {
    @ManyToOne
    @JoinColumn(name = "baggage_id", nullable = false)
    private Baggage baggage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public enum Action {
        SCANNED, LOADED, UNLOADED, DELIVERED
    }

    public Baggage getBaggage() {
        return baggage;
    }

    public void setBaggage(Baggage baggage) {
        this.baggage = baggage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "HandlingRecord{id=" + getId() + ", baggageId=" + baggage.getId() + ", userId=" + user.getId() + ", action=" + action + ", timestamp=" + timestamp + "}";
    }
}