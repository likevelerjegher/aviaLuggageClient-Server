package com.example.model;

import lombok.Data;

@Data
public class Baggage {
    private Long id;
    private double length;
    private double width;
    private double height;
    private double weight;
    private BaggageStatus status;
}
