package com.example.demo.service;

import com.example.demo.dto.FlightDTO;
import com.example.demo.model.Flight;
import com.example.demo.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Transactional
    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = new Flight(
                flightDTO.getFlightNumber(),
                flightDTO.getDepartureTime(),
                flightDTO.getArrivalTime(),
                flightDTO.getDestination()
        );
        flight = flightRepository.save(flight);
        return mapToDTO(flight);
    }

    @Transactional
    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setDestination(flightDTO.getDestination());
        flight = flightRepository.save(flight);
        return mapToDTO(flight);
    }

    public FlightDTO findById(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
        return mapToDTO(flight);
    }

    public List<FlightDTO> findAll() {
        return flightRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private FlightDTO mapToDTO(Flight flight) {
        return new FlightDTO(
                flight.getFlightId(),
                flight.getFlightNumber(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getDestination()
        );
    }
}
