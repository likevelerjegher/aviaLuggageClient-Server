package com.example.demo.repository;

import com.example.demo.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPassportNumber(String passportNumber);
    List<Client> findByFlightId(Long flightId);
}
