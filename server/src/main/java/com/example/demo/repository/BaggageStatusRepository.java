package com.example.demo.repository;

import com.example.demo.model.entity.BaggageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaggageStatusRepository extends JpaRepository<BaggageStatus, Long> {
    Optional<BaggageStatus> findByStatusName(BaggageStatus.StatusName statusName);
}