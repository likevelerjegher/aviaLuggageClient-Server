package com.example.model;

import lombok.Data;

@Data
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String passportNumber;
    private String ticketNumber;
    private Flight flight;
    private Baggage baggage;
    private String password;
}
