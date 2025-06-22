package com.example.demo.controller;

import com.example.demo.model.entity.Baggage;
import com.example.demo.model.entity.Client;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.Flight;
import com.example.demo.repository.BaggageRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.util.BaggageStatus;
import com.example.demo.config.SecurityConfig;
import com.example.demo.util.BaggageStatusHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/extra")
@RequiredArgsConstructor
public class ExtraOperationsController {

    private final BaggageRepository baggageRepository;
    private final ClientRepository clientRepository;
    private final FlightRepository flightRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/baggage/search")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Baggage> searchBaggage(@RequestParam(required = false) Long id,
                                       @RequestParam(required = false) BaggageStatus status) {
        if (id != null) {
            return baggageRepository.findById(id).map(List::of).orElse(List.of());
        } else if (status != null) {
            return baggageRepository.findAll()
                    .stream().filter(b -> b.getStatus() == status)
                    .toList();
        }
        return List.of();
    }
    @GetMapping("/clients/by-flight/{flightId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Client> getClientsByFlight(@PathVariable Long flightId) {
        return clientRepository.findAll()
                .stream()
                .filter(c -> c.getFlight() != null && c.getFlight().getId().equals(flightId))
                .toList();
    }

    @GetMapping("/baggage/by-flight/{flightId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Baggage> getBaggageByFlight(@PathVariable Long flightId) {
        return clientRepository.findAll()
                .stream()
                .filter(c -> c.getFlight() != null && c.getFlight().getId().equals(flightId))
                .map(Client::getBaggage)
                .filter(b -> b != null)
                .toList();
    }
    @GetMapping("/baggage/status-statistics")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Map<BaggageStatus, Long> getBaggageStatusStatistics() {
        return baggageRepository.findAll().stream()
                .collect(Collectors.groupingBy(Baggage::getStatus, Collectors.counting()));
    }
    @PutMapping("/baggage/{id}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Baggage updateBaggageStatus(@PathVariable Long id, @RequestParam BaggageStatus newStatus) {
        Baggage baggage = baggageRepository.findById(id).orElseThrow();
        // Здесь можно добавить запись в историю
        BaggageStatusHistory.addHistory(id, baggage.getStatus(), newStatus);
        baggage.setStatus(newStatus);
        return baggageRepository.save(baggage);
    }
    @GetMapping("/baggage/{id}/history")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<String> getBaggageHistory(@PathVariable Long id) {
        return BaggageStatusHistory.getHistory(id);
    }
    @GetMapping("/baggage/lost-report")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Baggage> getLostBaggageReport() {
        return baggageRepository.findAll().stream()
                .filter(b -> b.getStatus() != BaggageStatus.RECEIVED)
                .toList();
    }
    @GetMapping("/baggage/report-by-flight")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Map<Long, Long> getBaggageCountByFlight() {
        return clientRepository.findAll().stream()
                .filter(c -> c.getFlight() != null && c.getBaggage() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getFlight().getId(),
                        Collectors.counting()
                ));
    }
    @GetMapping("/flights/by-date")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Flight> getFlightsByDate(@RequestParam String date) {
        LocalDate queryDate = LocalDate.parse(date); // формат: YYYY-MM-DD
        return flightRepository.findAll().stream()
                .filter(f -> f.getDepartureDate() != null && f.getDepartureDate().equals(queryDate))
                .toList();
    }
    @PutMapping("/employee/change-password")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public String changeEmployeePassword(@RequestParam String currentPassword,
                                         @RequestParam String newPassword,
                                         Authentication auth) {
        String username = auth.getName();
        Employee employee = employeeRepository.findByUsername(username).orElseThrow();

        if (!passwordEncoder.matches(currentPassword, employee.getPassword())) {
            throw new RuntimeException("Wrong current password");
        }

        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
        return "Password updated";
    }
    @PutMapping("/client/change-password")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String changeClientPassword(@RequestParam String currentPassword,
                                       @RequestParam String newPassword,
                                       Authentication auth) {
        String passportNumber = auth.getName();
        Client client = clientRepository.findByPassportNumber(passportNumber).orElseThrow();

        if (!passwordEncoder.matches(currentPassword, client.getPassword())) {
            throw new RuntimeException("Wrong current password");
        }

        client.setPassword(passwordEncoder.encode(newPassword));
        clientRepository.save(client);
        return "Password updated";
    }

    @GetMapping("/baggage/total-count")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Map<String, Long> getTotalBaggageCount() {
        long count = baggageRepository.count();
        return Map.of("totalBaggage", count);
    }

}

