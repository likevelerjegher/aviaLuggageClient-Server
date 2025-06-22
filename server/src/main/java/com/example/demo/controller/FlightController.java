package com.example.demo.controller;

import com.example.demo.model.entity.Flight;
import com.example.demo.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightRepository flightRepository;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Flight createFlight(@RequestBody Flight flight) {
        return flightRepository.save(flight);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Flight updateFlight(@PathVariable Long id, @RequestBody Flight updatedFlight) {
        Flight flight = flightRepository.findById(id).orElseThrow();

        flight.setFlightNumber(updatedFlight.getFlightNumber());
        flight.setDeparture(updatedFlight.getDeparture());
        flight.setDestination(updatedFlight.getDestination());

        return flightRepository.save(flight);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public void deleteFlight(@PathVariable Long id) {
        flightRepository.deleteById(id);
    }
}
