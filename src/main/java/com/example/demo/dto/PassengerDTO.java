package com.example.demo.dto;

import java.io.Serializable;

public class PassengerDTO implements Serializable {
    private Long passengerId;
    private String fullName;
    private String passportNumber;
    private String contactInfo;

    public PassengerDTO() {}

    public PassengerDTO(Long passengerId, String fullName, String passportNumber, String contactInfo) {
        this.passengerId = passengerId;
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