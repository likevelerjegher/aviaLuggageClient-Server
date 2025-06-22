package com.example.demo.security;

import com.example.demo.model.entity.Client;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.Role;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Проверка среди сотрудников
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee != null) {
            return new org.springframework.security.core.userdetails.User(
                    employee.getUsername(),
                    employee.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(employee.getRole().name()))
            );
        }

        // Проверка среди клиентов
        Client client = clientRepository.findByPassportNumber(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                client.getPassportNumber(),
                client.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(Role.CLIENT.name()))
        );
    }
}
