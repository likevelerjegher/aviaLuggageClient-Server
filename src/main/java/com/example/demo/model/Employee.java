package com.example.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employees")
public class Employee implements User, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role; // ADMIN, EMPLOYEE, GUEST

    public Employee() {}

    public Employee(String fullName, String login, String passwordHash, String role) {
        this.fullName = fullName;
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    @Override
    public boolean authenticate(String login, String passwordHash) {
        return this.login.equals(login) && this.passwordHash.equals(passwordHash);
    }

    @Override
    public String getRole() {
        return role;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(String role) {
        this.role = role;
    }
}