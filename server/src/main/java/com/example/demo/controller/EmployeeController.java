package com.example.demo.controller;

import com.example.demo.model.dto.ClientDto;
import com.example.demo.model.entity.Client;
import com.example.demo.model.entity.Flight;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class EmployeeController {

    private final ClientRepository clientRepository;
    private final FlightRepository flightRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Client createClient(@RequestBody Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Client updateClient(@PathVariable Long id, @RequestBody ClientDto dto) {
        Client client = clientRepository.findById(id).orElseThrow();

        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setMiddleName(dto.getMiddleName());
        client.setPassportNumber(dto.getPassportNumber());
        client.setTicketNumber(dto.getTicketNumber());

        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
}
