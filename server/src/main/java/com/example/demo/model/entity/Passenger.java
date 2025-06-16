package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "passengers")
public class Passenger extends AbstractEntity implements Serializable {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "passport_number", unique = true, nullable = false)
    private String passportNumber;

    @Column(name = "ticket_number", unique = true, nullable = false)
    private String ticketNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public String toString() {
        return "Passenger{id=" + getId() + ", firstName='" + firstName + "', lastName='" + lastName + "', passportNumber='" + passportNumber + "', ticketNumber='" + ticketNumber + "'}";
    }
}