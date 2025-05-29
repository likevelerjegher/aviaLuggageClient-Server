package com.example.demo.controller;

import com.example.demo.dto.FlightDTO;
import com.example.demo.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO flightDTO) {
        return ResponseEntity.ok(flightService.createFlight(flightDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'GUEST')")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.findAll());
    }
}