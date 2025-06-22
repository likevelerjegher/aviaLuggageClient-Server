package com.example.demo.repository;

import com.example.demo.model.entity.Baggage;
import com.example.demo.util.BaggageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {
    List<Baggage> findByStatus(BaggageStatus status);

}
