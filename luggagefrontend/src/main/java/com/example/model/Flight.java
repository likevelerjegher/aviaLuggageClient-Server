package com.example.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Flight {
    private Long id;
    private String flightNumber;
    private String departure;
    private String destination;
    private LocalDate departureDate;
}
