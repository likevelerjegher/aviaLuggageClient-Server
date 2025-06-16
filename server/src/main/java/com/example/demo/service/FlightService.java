package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.dto.FlightDTO;
import com.example.demo.model.entity.Flight;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
        return convertToDTO(flight);
    }

    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = new Flight();
        updateFlightFromDTO(flight, flightDTO);
        flight = flightRepository.save(flight);
        return convertToDTO(flight);
    }

    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
        updateFlightFromDTO(flight, flightDTO);
        flight = flightRepository.save(flight);
        return convertToDTO(flight);
    }

    public String getFlightStatus(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(flight.getDepartureTime())) {
            return "Scheduled";
        } else if (now.isAfter(flight.getArrivalTime())) {
            return "Arrived";
        } else {
            return "In Transit";
        }
    }

    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setDestination(flight.getDestination());
        return dto;
    }

    private void updateFlightFromDTO(Flight flight, FlightDTO dto) {
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setDestination(dto.getDestination());
    }
}
