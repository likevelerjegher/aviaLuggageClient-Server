package com.example.demo.dto;

import java.io.Serializable;

public class LuggageDTO implements Serializable {
    private Long luggageId;
    private String luggageType; // PASSENGER or CARGO
    private double weight;
    private String status;
    private Long passengerId;
    private Long flightId;
    private String cargoType;

    public LuggageDTO() {}

    public LuggageDTO(Long luggageId, String luggageType, double weight, String status, Long passengerId, Long flightId, String cargoType) {
        this.luggageId = luggageId;
        this.luggageType = luggageType;
        this.weight = weight;
        this.status = status;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.cargoType = cargoType;
    }

    public Long getLuggageId() {
        return luggageId;
    }

    public void setLuggageId(Long luggageId) {
        this.luggageId = luggageId;
    }

    public String getLuggageType() {
        return luggageType;
    }

    public void setLuggageType(String luggageType) {
        this.luggageType = luggageType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }
}