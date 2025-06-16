package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "baggage")
public class Baggage extends AbstractEntity implements Serializable {
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String dimensions;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private BaggageStatus status;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaggageStatus getStatus() {
        return status;
    }

    public void setStatus(BaggageStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Baggage{id=" + getId() + ", passengerId=" + passenger.getId() + ", flightId=" + flight.getId() + ", weight=" + weight + ", dimensions='" + dimensions + "', type='" + type + "', status=" + status.getStatusName() + "}";
    }
}