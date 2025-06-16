package com.example.demo.repository;

import com.example.demo.model.entity.HandlingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HandlingRecordRepository extends JpaRepository<HandlingRecord, Long> {
    List<HandlingRecord> findByBaggageId(Long baggageId);
}