package com.example.demo.service;

import com.example.demo.dto.LuggageDTO;
import com.example.demo.model.AbstractLuggage;
import com.example.demo.model.CargoLuggage;
import com.example.demo.model.Flight;
import com.example.demo.model.Passenger;
import com.example.demo.model.PassengerLuggage;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.LuggageRepository;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.exception.LuggageNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class LuggageService {
    private final LuggageRepository luggageRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public LuggageService(LuggageRepository luggageRepository, PassengerRepository passengerRepository, FlightRepository flightRepository) {
        this.luggageRepository = luggageRepository;
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
    }

    @Transactional
    public LuggageDTO registerLuggage(LuggageDTO luggageDTO) {
        Flight flight = flightRepository.findById(luggageDTO.getFlightId())
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + luggageDTO.getFlightId()));

        AbstractLuggage luggage;
        if ("PASSENGER".equals(luggageDTO.getLuggageType())) {
            Passenger passenger = passengerRepository.findById(luggageDTO.getPassengerId())
                    .orElseThrow(() -> new IllegalArgumentException("Passenger not found: " + luggageDTO.getPassengerId()));
            luggage = new PassengerLuggage(luggageDTO.getLuggageId(), luggageDTO.getWeight(), luggageDTO.getStatus(), flight, passenger);
        } else if ("CARGO".equals(luggageDTO.getLuggageType())) {
            luggage = new CargoLuggage(luggageDTO.getLuggageId(), luggageDTO.getWeight(), luggageDTO.getStatus(), flight, luggageDTO.getCargoType());
        } else {
            throw new IllegalArgumentException("Invalid luggage type: " + luggageDTO.getLuggageType());
        }

        luggage = luggageRepository.save(luggage);
        return mapToDTO(luggage);
    }

    @Transactional
    public LuggageDTO updateStatus(Long luggageId, String status) {
        AbstractLuggage luggage = luggageRepository.findById(luggageId)
                .orElseThrow(() -> new LuggageNotFoundException("Luggage not found: " + luggageId));
        luggage.setStatus(status);
        luggage = luggageRepository.save(luggage);
        return mapToDTO(luggage);
    }

    public void processLuggageAsync(Long luggageId, String checkpoint) {
        executorService.submit(() -> {
            AbstractLuggage luggage = luggageRepository.findById(luggageId)
                    .orElseThrow(() -> new LuggageNotFoundException("Luggage not found: " + luggageId));
            luggage.processLuggage(checkpoint);
            luggageRepository.save(luggage);
        });
    }

    public LuggageDTO findById(Long luggageId) {
        AbstractLuggage luggage = luggageRepository.findById(luggageId)
                .orElseThrow(() -> new LuggageNotFoundException("Luggage not found: " + luggageId));
        return mapToDTO(luggage);
    }

    public List<LuggageDTO> findByPassengerId(Long passengerId) {
        return luggageRepository.findByPassengerId(passengerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LuggageDTO> findLostLuggage() {
        return luggageRepository.findByStatus("LOST").stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private LuggageDTO mapToDTO(AbstractLuggage luggage) {
        if (luggage instanceof PassengerLuggage passengerLuggage) {
            return new LuggageDTO(
                    luggage.getLuggageId(),
                    "PASSENGER",
                    luggage.getWeight(),
                    luggage.getStatus(),
                    passengerLuggage.getPassenger().getPassengerId(),
                    luggage.getFlight().getFlightId(),
                    null
            );
        } else if (luggage instanceof CargoLuggage cargoLuggage) {
            return new LuggageDTO(
                    luggage.getLuggageId(),
                    "CARGO",
                    luggage.getWeight(),
                    luggage.getStatus(),
                    null,
                    luggage.getFlight().getFlightId(),
                    cargoLuggage.getCargoType()
            );
        }
        throw new IllegalStateException("Unknown luggage type");
    }
}