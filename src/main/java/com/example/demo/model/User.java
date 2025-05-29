package com.example.demo.model;

public interface User {
    boolean authenticate(String login, String passwordHash);
    String getRole();
}