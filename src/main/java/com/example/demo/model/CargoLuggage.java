package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CARGO")
public class CargoLuggage extends AbstractLuggage {
    @Column(name = "cargo_type")
    private String cargoType;

    public CargoLuggage() {}

    public CargoLuggage(Long luggageId, double weight, String status, Flight flight, String cargoType) {
        super(luggageId, weight, status, flight, null); // passenger is null for cargo
        this.cargoType = cargoType;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    @Override
    public void processLuggage() {
        setStatus("Processed Cargo");
    }
}