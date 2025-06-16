package com.example.model;

import java.io.Serializable;

public class Baggage implements Serializable {
    private Long id;
    private Long passengerId;
    private Long flightId;
    private Double weight;
    private String dimensions;
    private String type;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "BaggageView{id=" + id + ", passengerId=" + passengerId + ", flightId=" + flightId + ", weight=" + weight +
                ", dimensions='" + dimensions + "', type='" + type + "', status='" + status + "'}";
    }
}