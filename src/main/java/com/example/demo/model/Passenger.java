package com.example.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "passengers")
public class Passenger implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "passport_number", unique = true, nullable = false)
    private String passportNumber;

    @Column(name = "contact_info")
    private String contactInfo;

    public Passenger() {}

    public Passenger(String fullName, String passportNumber, String contactInfo) {
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.contactInfo = contactInfo;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}