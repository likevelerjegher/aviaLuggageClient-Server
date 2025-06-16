package com.example.demo.controller;

import com.example.demo.model.dto.AuthRequestDTO;
import com.example.demo.model.dto.AuthResponseDTO;
import com.example.demo.model.dto.PassengerDTO;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'USER')")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        return ResponseEntity.ok(passengerService.createPassenger(passengerDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable Long id, @RequestBody PassengerDTO passengerDTO) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passengerDTO));
    }

    @PostMapping("/{id}/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<AuthResponseDTO> createUserForPassenger(@PathVariable Long id, @RequestBody AuthRequestDTO authRequest) {
        return ResponseEntity.ok(passengerService.createUserForPassenger(id, authRequest));
    }
}