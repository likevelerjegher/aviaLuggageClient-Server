package com.example.api;

import com.example.model.AuthResponse;
import com.example.model.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthService {

    public static AuthResponse login(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);
        String response = ApiClient.post("/auth/login", request);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, AuthResponse.class);
    }
}

