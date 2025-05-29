package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PASSENGER")
public class PassengerLuggage extends AbstractLuggage {
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    public PassengerLuggage() {}

    public PassengerLuggage(Long luggageId, double weight, String status, Flight flight, Passenger passenger) {
        super(luggageId, weight, status, flight, null); // passenger is null for cargo
        this.passenger = passenger;
    }

    @Override
    public void processLuggage() {
        setStatus("Processed for passenger: " + passenger.getFullName());
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}