package com.example.demo.repository;

import com.example.demo.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByPassportNumber(String passportNumber);
    Optional<Passenger> findByTicketNumber(String ticketNumber);
}