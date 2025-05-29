package com.example.demo.dto;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {
    private Long employeeId;
    private String fullName;
    private String login;
    private String role;

    public EmployeeDTO() {}

    public EmployeeDTO(Long employeeId, String fullName, String login, String role) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.login = login;
        this.role = role;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}