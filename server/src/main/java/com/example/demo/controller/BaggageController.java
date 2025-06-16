package com.example.demo.controller;

import com.example.demo.model.dto.BaggageDTO;
import com.example.demo.service.BaggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baggage")
public class BaggageController {
    @Autowired
    private BaggageService baggageService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<BaggageDTO>> getAllBaggage() {
        return ResponseEntity.ok(baggageService.getAllBaggage());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'USER')")
    public ResponseEntity<BaggageDTO> getBaggageById(@PathVariable Long id) {
        return ResponseEntity.ok(baggageService.getBaggageById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<BaggageDTO> createBaggage(@RequestBody BaggageDTO baggageDTO) {
        return ResponseEntity.ok(baggageService.createBaggage(baggageDTO));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<BaggageDTO> updateBaggageStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(baggageService.updateBaggageStatus(id, status));
    }

    @PostMapping("/{id}/handling")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<BaggageDTO> addHandlingRecord(@PathVariable Long id, @RequestParam String action) {
        return ResponseEntity.ok(baggageService.addHandlingRecord(id, action));
    }

    @GetMapping("/passenger/{passengerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'USER')")
    public ResponseEntity<List<BaggageDTO>> getBaggageByPassenger(@PathVariable Long passengerId) {
        return ResponseEntity.ok(baggageService.getBaggageByPassenger(passengerId));
    }

    @GetMapping("/flight/{flightId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<BaggageDTO>> getBaggageByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(baggageService.getBaggageByFlight(flightId));
    }
}