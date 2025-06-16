package com.example.demo.controller;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/employee")
    public ResponseEntity<UserDTO> createEmployee(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createEmployee(userDTO));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<UserDTO>> getAllEmployees() {
        return ResponseEntity.ok(userService.getAllEmployees());
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        userService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<UserDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateEmployee(id, userDTO));
    }
}