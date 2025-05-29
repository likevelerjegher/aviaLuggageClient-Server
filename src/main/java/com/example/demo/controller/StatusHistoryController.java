package com.example.demo.controller;

import com.example.demo.dto.StatusHistoryDTO;
import com.example.demo.service.StatusHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-history")
public class StatusHistoryController {
    private final StatusHistoryService statusHistoryService;

    public StatusHistoryController(StatusHistoryService statusHistoryService) {
        this.statusHistoryService = statusHistoryService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<StatusHistoryDTO> addStatusHistory(@RequestBody StatusHistoryDTO statusHistoryDTO) {
        return ResponseEntity.ok(statusHistoryService.addStatusHistory(statusHistoryDTO));
    }

    @GetMapping("/luggage/{luggageId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'GUEST')")
    public ResponseEntity<List<StatusHistoryDTO>> getHistoryByLuggageId(@PathVariable Long luggageId) {
        return ResponseEntity.ok(statusHistoryService.findByLuggageId(luggageId));
    }

    @GetMapping("/flight/{flightId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<StatusHistoryDTO>> getHistoryByFlightId(@PathVariable Long flightId) {
        return ResponseEntity.ok(statusHistoryService.findByFlightId(flightId));
    }
}