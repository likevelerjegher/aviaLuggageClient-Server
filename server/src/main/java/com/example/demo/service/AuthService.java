package com.example.demo.service;


import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(String username, String password) {
        logger.info("Attempting authentication for user: " + username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            logger.info("Authentication successful for user: " + username);
            return jwtUtil.generateToken(authentication);
        } catch (BadCredentialsException e) {
            logger.warning("Invalid credentials for user: " + username);
            throw new UnauthorizedException("Invalid username or password");
        } catch (AuthenticationException e) {
            logger.severe("Authentication error for user: " + username + " - " + e.getMessage());
            throw new UnauthorizedException("Authentication failed: " + e.getMessage());
        }
    }

    public String register(String username, String password, String role) {
        logger.info("Registering user: " + username + " with role: " + role);
        if (userRepository.findByUsername(username).isPresent()) {
            logger.warning("Username already exists: " + username);
            throw new UnauthorizedException("Username already exists");
        }
        User.Role userRole;
        try {
            userRole = User.Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid role: " + role);
            throw new UnauthorizedException("Invalid role: " + role);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encodes with {bcrypt}
        user.setRole(userRole);
        userRepository.save(user);
        logger.info("User registered: " + username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                user.getAuthorities()
        );
        String token = jwtUtil.generateToken(authentication);
        logger.info("Token generated for user: " + username);
        return token;
    }
}