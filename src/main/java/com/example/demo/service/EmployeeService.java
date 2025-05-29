package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(
                employeeDTO.getFullName(),
                employeeDTO.getLogin(),
                hashPassword(employeeDTO.getLogin()), // Placeholder for password hashing
                employeeDTO.getRole()
        );
        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    @Transactional
    public EmployeeDTO updateRole(Long employeeId, String role) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));
        employee.setRole(role);
        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    public EmployeeDTO findById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));
        return mapToDTO(employee);
    }

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getLogin(),
                employee.getRole()
        );
    }

    private String hashPassword(String login) {
        // Placeholder for password hashing (e.g., using BCrypt)
        return "hashed_" + login;
    }
}