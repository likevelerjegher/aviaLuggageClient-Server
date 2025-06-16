package com.example.demo.controller;

import com.example.demo.model.dto.AuthRequestDTO;
import com.example.demo.model.dto.AuthResponseDTO;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        String token = authService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRequestDTO request) {
        String token = authService.register(request.getUsername(), request.getPassword(), "EMPLOYEE");
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}