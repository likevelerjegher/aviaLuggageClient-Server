package com.example.demo.service;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createEmployee(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UnauthorizedException("Username already exists");
        }
        User.Role userRole;
        try {
            userRole = User.Role.valueOf(userDTO.getRole());
            if (userRole != User.Role.EMPLOYEE) {
                throw new UnauthorizedException("Only EMPLOYEE role is allowed");
            }
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("Invalid role: " + userDTO.getRole());
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userRole);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setRole(user.getRole().name());
        return responseDTO;
    }

    public List<UserDTO> getAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.EMPLOYEE)
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setRole(user.getRole().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void deleteEmployee(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        if (user.getRole() != User.Role.EMPLOYEE) {
            throw new UnauthorizedException("Only EMPLOYEE users can be deleted");
        }
        userRepository.delete(user);
    }

    public UserDTO updateEmployee(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        if (user.getRole() != User.Role.EMPLOYEE) {
            throw new UnauthorizedException("Only EMPLOYEE users can be updated");
        }
        if (!user.getUsername().equals(userDTO.getUsername()) &&
                userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UnauthorizedException("Username already exists");
        }
        try {
            User.Role userRole = User.Role.valueOf(userDTO.getRole());
            if (userRole != User.Role.EMPLOYEE) {
                throw new UnauthorizedException("Only EMPLOYEE role is allowed");
            }
            user.setRole(userRole);
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("Invalid role: " + userDTO.getRole());
        }
        user.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user = userRepository.save(user);

        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setRole(user.getRole().name());
        return responseDTO;
    }
}