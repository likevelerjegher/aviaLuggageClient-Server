package com.example.model;

import lombok.Data;

@Data
public class Employee {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
