package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.dto.PassengerDTO;
import com.example.demo.model.dto.AuthRequestDTO;
import com.example.demo.model.dto.AuthResponseDTO;
import com.example.demo.model.entity.Passenger;
import com.example.demo.model.entity.User;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder securityPasswordEncoder;
    @Autowired
    private JwtUtil jwtService;

    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PassengerDTO getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        return convertToDTO(passenger);
    }

    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        updatePassengerFromDTO(passenger, passengerDTO);
        passenger = passengerRepository.save(passenger);
        return convertToDTO(passenger);
    }

    public PassengerDTO updatePassenger(Long id, PassengerDTO passengerDTO) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        updatePassengerFromDTO(passenger, passengerDTO);
        passenger = passengerRepository.save(passenger);
        return convertToDTO(passenger);
    }

    public AuthResponseDTO createUserForPassenger(Long id, AuthRequestDTO authRequest) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
            throw new UnauthorizedException("Username already exists");
        }
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(securityPasswordEncoder.encode(authRequest.getPassword()));
        user.setRole(User.Role.USER);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
        String token = jwtService.generateToken(authentication);
        return new AuthResponseDTO(token);
    }

    private PassengerDTO convertToDTO(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(passenger.getId());
        dto.setFirstName(passenger.getFirstName());
        dto.setLastName(passenger.getLastName());
        dto.setPassportNumber(passenger.getPassportNumber());
        dto.setTicketNumber(passenger.getTicketNumber());
        return dto;
    }

    private void updatePassengerFromDTO(Passenger passenger, PassengerDTO dto) {
        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());
        passenger.setPassportNumber(dto.getPassportNumber());
        passenger.setTicketNumber(dto.getTicketNumber());
    }
}