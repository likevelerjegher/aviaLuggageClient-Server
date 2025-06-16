package com.example.demo.repository;

import com.example.demo.model.entity.Baggage;
import com.example.demo.model.entity.BaggageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {
    List<Baggage> findByPassengerId(Long passengerId);
    List<Baggage> findByFlightId(Long flightId);
    List<Baggage> findByStatusStatusName(BaggageStatus.StatusName statusName);
    long countByStatusStatusName(BaggageStatus.StatusName statusName);
}