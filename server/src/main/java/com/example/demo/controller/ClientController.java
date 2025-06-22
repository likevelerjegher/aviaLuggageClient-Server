package com.example.demo.controller;

import com.example.demo.model.entity.Baggage;
import com.example.demo.model.entity.Client;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public Client getMyInfo(Authentication authentication) {
        String passportNumber = authentication.getName();
        return clientRepository.findByPassportNumber(passportNumber).orElseThrow();
    }

}
