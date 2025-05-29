package com.example.demo.service;

import com.example.demo.dto.CheckpointDTO;
import com.example.demo.model.Checkpoint;
import com.example.demo.repository.CheckpointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckpointService {
    private final CheckpointRepository checkpointRepository;

    public CheckpointService(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    @Transactional
    public CheckpointDTO createCheckpoint(CheckpointDTO checkpointDTO) {
        Checkpoint checkpoint = new Checkpoint(
                checkpointDTO.getCheckpointName(),
                checkpointDTO.getLocation()
        );
        checkpoint = checkpointRepository.save(checkpoint);
        return mapToDTO(checkpoint);
    }

    public CheckpointDTO findById(Long checkpointId) {
        Checkpoint checkpoint = checkpointRepository.findById(checkpointId)
                .orElseThrow(() -> new IllegalArgumentException("Checkpoint not found: " + checkpointId));
        return mapToDTO(checkpoint);
    }

    public List<CheckpointDTO> findAll() {
        return checkpointRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CheckpointDTO mapToDTO(Checkpoint checkpoint) {
        return new CheckpointDTO(
                checkpoint.getCheckpointId(),
                checkpoint.getCheckpointName(),
                checkpoint.getLocation()
        );
    }
}