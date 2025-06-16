package com.example.demo.service;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private PasswordEncoder securityPasswordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO createEmployee(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userDTO.getRole() != User.Role.EMPLOYEE) {
            throw new RuntimeException("Only EMPLOYEE role can be assigned");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(securityPasswordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDTO> getAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.EMPLOYEE)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEmployee(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO updateEmployee(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(User.Role.EMPLOYEE)) {
            throw new RuntimeException("Can only update EMPLOYEE users");
        }
        if (userDTO.getUsername() != null && !userDTO.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(securityPasswordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getRole() != null && userDTO.getRole() == User.Role.EMPLOYEE) {
            user.setRole(userDTO.getRole());
        }

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}