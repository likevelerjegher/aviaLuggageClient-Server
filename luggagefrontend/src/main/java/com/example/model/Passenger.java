package com.example.model;

import java.io.Serializable;

public class Passenger implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String ticketNumber;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }
    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    @Override
    public String toString() {
        return "Passenger{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', passportNumber='" + passportNumber + "', ticketNumber='" + ticketNumber + "'}";
    }
}