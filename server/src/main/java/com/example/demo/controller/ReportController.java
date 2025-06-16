package com.example.demo.controller;

import com.example.demo.model.dto.BaggageDTO;
import com.example.demo.service.BaggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private BaggageService baggageService;

    @GetMapping("/lost-baggage")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<BaggageDTO>> getLostBaggageReport() {
        return ResponseEntity.ok(baggageService.getLostBaggageReport());
    }

    @GetMapping("/baggage-statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getBaggageStatistics() {
        return ResponseEntity.ok(baggageService.getBaggageStatistics());
    }
}