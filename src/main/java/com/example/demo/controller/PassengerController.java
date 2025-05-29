package com.example.demo.controller;

import com.example.demo.dto.PassengerDTO;
import com.example.demo.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<PassengerDTO> registerPassenger(@RequestBody PassengerDTO passengerDTO) {
        return ResponseEntity.ok(passengerService.registerPassenger(passengerDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'GUEST')")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.findById(id));
    }

    @GetMapping("/passport/{passportNumber}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'GUEST')")
    public ResponseEntity<PassengerDTO> getPassengerByPassport(@PathVariable String passportNumber) {
        return ResponseEntity.ok(passengerService.findByPassportNumber(passportNumber));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.findAll());
    }
}