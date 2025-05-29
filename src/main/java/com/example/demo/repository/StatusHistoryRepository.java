package com.example.demo.repository;

import com.example.demo.model.LuggageStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusHistoryRepository extends JpaRepository<LuggageStatusHistory, Long> {
    List<LuggageStatusHistory> findByLuggageLuggageId(Long luggageId);

    @Query("SELECT h FROM LuggageStatusHistory h WHERE h.luggage.flight.flightId = :flightId")
    List<LuggageStatusHistory> findByLuggageFlightId(Long flightId);
}