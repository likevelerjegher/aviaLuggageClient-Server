package com.example.demo.service;

import com.example.demo.dto.StatusHistoryDTO;
import com.example.demo.model.AbstractLuggage;
import com.example.demo.model.Employee;
import com.example.demo.model.LuggageStatusHistory;
import com.example.demo.repository.LuggageRepository;
import com.example.demo.repository.StatusHistoryRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusHistoryService {
    private final StatusHistoryRepository statusHistoryRepository;
    private final LuggageRepository luggageRepository;
    private final EmployeeRepository employeeRepository;

    public StatusHistoryService(StatusHistoryRepository statusHistoryRepository, LuggageRepository luggageRepository, EmployeeRepository employeeRepository) {
        this.statusHistoryRepository = statusHistoryRepository;
        this.luggageRepository = luggageRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public StatusHistoryDTO addStatusHistory(StatusHistoryDTO statusHistoryDTO) {
        AbstractLuggage luggage = luggageRepository.findById(statusHistoryDTO.getLuggageId())
                .orElseThrow(() -> new IllegalArgumentException("Luggage not found: " + statusHistoryDTO.getLuggageId()));
        Employee employee = statusHistoryDTO.getEmployeeId() != null
                ? employeeRepository.findById(statusHistoryDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + statusHistoryDTO.getEmployeeId()))
                : null;

        LuggageStatusHistory statusHistory = new LuggageStatusHistory(
                luggage,
                statusHistoryDTO.getStatus(),
                statusHistoryDTO.getTimestamp() != null ? statusHistoryDTO.getTimestamp() : LocalDateTime.now(),
                employee
        );
        statusHistory = statusHistoryRepository.save(statusHistory);
        return mapToDTO(statusHistory);
    }

    public List<StatusHistoryDTO> findByLuggageId(Long luggageId) {
        return statusHistoryRepository.findByLuggageLuggageId(luggageId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<StatusHistoryDTO> findByFlightId(Long flightId) {
        return statusHistoryRepository.findByLuggageFlightId(flightId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private StatusHistoryDTO mapToDTO(LuggageStatusHistory statusHistory) {
        return new StatusHistoryDTO(
                statusHistory.getHistoryId(),
                statusHistory.getLuggage().getLuggageId(),
                statusHistory.getStatus(),
                statusHistory.getTimestamp(),
                statusHistory.getEmployee() != null ? statusHistory.getEmployee().getEmployeeId() : null
        );
    }
}