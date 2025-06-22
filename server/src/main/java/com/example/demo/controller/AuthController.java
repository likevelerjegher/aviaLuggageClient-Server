package com.example.demo.controller;

import com.example.demo.model.dto.LoginRequestDto;
import com.example.demo.model.entity.Client;
import com.example.demo.model.entity.Employee;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDto request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // Поиск среди сотрудников
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee != null && passwordEncoder.matches(password, employee.getPassword())) {
            String token = jwtTokenProvider.generateToken(username, employee.getRole().name());
            return Map.of("token", token, "role", employee.getRole().name());
        }

        // Поиск среди клиентов
        Client client = clientRepository.findByPassportNumber(username).orElse(null);
        if (client != null && passwordEncoder.matches(password, client.getPassword())) {
            String token = jwtTokenProvider.generateToken(username, "CLIENT");
            return Map.of("token", token, "role", "CLIENT");
        }

        throw new RuntimeException("Invalid username or password");
    }
}
