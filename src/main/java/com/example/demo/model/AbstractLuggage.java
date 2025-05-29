package com.example.demo.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "luggage")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "luggage_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractLuggage implements Serializable {
    @Id
    private Long luggageId;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "passenger_id") // Nullable by default
    private Passenger passenger;

    public AbstractLuggage() {}

    public AbstractLuggage(Long luggageId, double weight, String status, Flight flight, Passenger passenger) {
        this.luggageId = luggageId;
        this.weight = weight;
        this.status = status;
        this.flight = flight;
        this.passenger = passenger;
    }

    public Long getLuggageId() {
        return luggageId;
    }

    public void setLuggageId(Long luggageId) {
        this.luggageId = luggageId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public abstract void processLuggage();

    public void processLuggage(String checkpoint) {
        this.status = "Processed at " + checkpoint;
    }
}