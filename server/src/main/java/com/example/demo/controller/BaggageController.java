package com.example.demo.controller;

import com.example.demo.model.entity.Baggage;
import com.example.demo.repository.BaggageRepository;
import com.example.demo.util.BaggageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baggage")
@RequiredArgsConstructor
public class BaggageController {

    private final BaggageRepository baggageRepository;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Baggage> getAllBaggage() {
        return baggageRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Baggage createBaggage(@RequestBody Baggage baggage) {
        return baggageRepository.save(baggage);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Baggage updateBaggage(@PathVariable Long id, @RequestBody Baggage updatedBaggage) {
        Baggage baggage = baggageRepository.findById(id).orElseThrow();

        baggage.setLength(updatedBaggage.getLength());
        baggage.setWidth(updatedBaggage.getWidth());
        baggage.setHeight(updatedBaggage.getHeight());
        baggage.setWeight(updatedBaggage.getWeight());
        baggage.setStatus(updatedBaggage.getStatus());

        return baggageRepository.save(baggage);
    }

}
