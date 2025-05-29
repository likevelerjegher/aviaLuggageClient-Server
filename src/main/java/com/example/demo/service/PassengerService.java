package com.example.demo.service;

import com.example.demo.dto.PassengerDTO;
import com.example.demo.model.Passenger;
import com.example.demo.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Transactional
    public PassengerDTO registerPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger(
                passengerDTO.getFullName(),
                passengerDTO.getPassportNumber(),
                passengerDTO.getContactInfo()
        );
        passenger = passengerRepository.save(passenger);
        return mapToDTO(passenger);
    }

    public PassengerDTO findById(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found: " + passengerId));
        return mapToDTO(passenger);
    }

    public PassengerDTO findByPassportNumber(String passportNumber) {
        Passenger passenger = passengerRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with passport: " + passportNumber));
        return mapToDTO(passenger);
    }

    public List<PassengerDTO> findAll() {
        return passengerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PassengerDTO mapToDTO(Passenger passenger) {
        return new PassengerDTO(
                passenger.getPassengerId(),
                passenger.getFullName(),
                passenger.getPassportNumber(),
                passenger.getContactInfo()
        );
    }
}