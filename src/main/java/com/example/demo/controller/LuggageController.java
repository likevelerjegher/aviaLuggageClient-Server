package com.example.demo.controller;

import com.example.demo.dto.LuggageDTO;
import com.example.demo.service.LuggageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/luggage")
public class LuggageController {
    private final LuggageService luggageService;

    public LuggageController(LuggageService luggageService) {
        this.luggageService = luggageService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<LuggageDTO> registerLuggage(@RequestBody LuggageDTO luggageDTO) {
        return ResponseEntity.ok(luggageService.registerLuggage(luggageDTO));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<LuggageDTO> updateStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok(luggageService.updateStatus(id, status));
    }

    @PostMapping("/{id}/process")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Void> processLuggage(@PathVariable Long id, @RequestBody String checkpoint) {
        luggageService.processLuggageAsync(id, checkpoint);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'GUEST')")
    public ResponseEntity<LuggageDTO> getLuggageById(@PathVariable Long id) {
        return ResponseEntity.ok(luggageService.findById(id));
    }

    @GetMapping("/passenger/{passengerId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'GUEST')")
    public ResponseEntity<List<LuggageDTO>> getLuggageByPassengerId(@PathVariable Long passengerId) {
        return ResponseEntity.ok(luggageService.findByPassengerId(passengerId));
    }

    @GetMapping("/lost")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<LuggageDTO>> getLostLuggage() {
        return ResponseEntity.ok(luggageService.findLostLuggage());
    }
}