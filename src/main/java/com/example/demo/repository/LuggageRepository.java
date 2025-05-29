package com.example.demo.repository;

import com.example.demo.model.AbstractLuggage;
import com.example.demo.model.PassengerLuggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LuggageRepository extends JpaRepository<AbstractLuggage, Long> {
    @Query("SELECT l FROM PassengerLuggage l WHERE l.passenger.passengerId = :passengerId")
    List<PassengerLuggage> findByPassengerId(Long passengerId);

    List<AbstractLuggage> findByStatus(String status);
}