package com.example.demo.model.dto;

public class EmployeeDto {
    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EmployeeDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
