package com.example.model;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Flight implements Serializable {
    private Long id;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String destination;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    @Override
    public String toString() {
        return "Flight{id=" + id + ", flightNumber='" + flightNumber + "', departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime + "', destination='" + destination + "'}";
    }
}