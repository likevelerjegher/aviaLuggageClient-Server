package com.example.demo.controller;

import com.example.demo.dto.CheckpointDTO;
import com.example.demo.service.CheckpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkpoints")
public class CheckpointController {
    private final CheckpointService checkpointService;

    public CheckpointController(CheckpointService checkpointService) {
        this.checkpointService = checkpointService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CheckpointDTO> createCheckpoint(@RequestBody CheckpointDTO checkpointDTO) {
        return ResponseEntity.ok(checkpointService.createCheckpoint(checkpointDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<CheckpointDTO> getCheckpointById(@PathVariable Long id) {
        return ResponseEntity.ok(checkpointService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<CheckpointDTO>> getAllCheckpoints() {
        return ResponseEntity.ok(checkpointService.findAll());
    }
}