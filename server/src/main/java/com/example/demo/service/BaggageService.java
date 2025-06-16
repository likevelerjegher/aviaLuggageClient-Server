package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.dto.BaggageDTO;
import com.example.demo.model.entity.Baggage;
import com.example.demo.model.entity.BaggageStatus;
import com.example.demo.model.entity.Flight;
import com.example.demo.model.entity.HandlingRecord;
import com.example.demo.model.entity.Passenger;
import com.example.demo.repository.BaggageRepository;
import com.example.demo.repository.BaggageStatusRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.HandlingRecordRepository;
import com.example.demo.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaggageService {
    @Autowired
    private BaggageRepository baggageRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private BaggageStatusRepository statusRepository;
    @Autowired
    private HandlingRecordRepository handlingRecordRepository;

    public List<BaggageDTO> getAllBaggage() {
        return baggageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BaggageDTO getBaggageById(Long id) {
        Baggage baggage = baggageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baggage not found"));
        return convertToDTO(baggage);
    }

    public BaggageDTO createBaggage(BaggageDTO baggageDTO) {
        Baggage baggage = new Baggage();
        updateBaggageFromDTO(baggage, baggageDTO);
        baggage = baggageRepository.save(baggage);
        return convertToDTO(baggage);
    }

    public BaggageDTO updateBaggageStatus(Long id, String status) {
        Baggage baggage = baggageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baggage not found"));
        BaggageStatus baggageStatus = statusRepository.findByStatusName(BaggageStatus.StatusName.valueOf(status))
                .orElseThrow(() -> new ResourceNotFoundException("Status not found"));
        baggage.setStatus(baggageStatus);
        baggage = baggageRepository.save(baggage);
        return convertToDTO(baggage);
    }

    public BaggageDTO addHandlingRecord(Long id, String action) {
        Baggage baggage = baggageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baggage not found"));
        HandlingRecord record = new HandlingRecord();
        record.setBaggage(baggage);
        record.setAction(HandlingRecord.Action.valueOf(action));
        handlingRecordRepository.save(record);
        return convertToDTO(baggage);
    }

    public List<BaggageDTO> getBaggageByPassenger(Long passengerId) {
        passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        return baggageRepository.findByPassengerId(passengerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BaggageDTO> getBaggageByFlight(Long flightId) {
        flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
        return baggageRepository.findByFlightId(flightId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BaggageDTO> getLostBaggageReport() {
        return baggageRepository.findByStatusStatusName(BaggageStatus.StatusName.LOST).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public String getBaggageStatistics() {
        long total = baggageRepository.count();
        long lost = baggageRepository.countByStatusStatusName(BaggageStatus.StatusName.LOST);
        return String.format("Total baggage: %d, Lost baggage: %d (%.2f%%)", total, lost, (double) lost / total * 100);
    }

    private BaggageDTO convertToDTO(Baggage baggage) {
        BaggageDTO dto = new BaggageDTO();
        dto.setId(baggage.getId());
        dto.setPassengerId(baggage.getPassenger().getId());
        dto.setFlightId(baggage.getFlight().getId());
        dto.setWeight(baggage.getWeight());
        dto.setDimensions(baggage.getDimensions());
        dto.setType(baggage.getType());
        dto.setStatus(baggage.getStatus().getStatusName().toString());
        return dto;
    }

    private void updateBaggageFromDTO(Baggage baggage, BaggageDTO dto) {
        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
        BaggageStatus status = statusRepository.findByStatusName(BaggageStatus.StatusName.valueOf(dto.getStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("Status not found"));
        baggage.setPassenger(passenger);
        baggage.setFlight(flight);
        baggage.setWeight(dto.getWeight());
        baggage.setDimensions(dto.getDimensions());
        baggage.setType(dto.getType());
        baggage.setStatus(status);
    }
}