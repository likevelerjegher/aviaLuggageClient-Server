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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.logging.Logger;

@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private PasswordEncoder securityPasswordEncoder;

    public String authenticate(String username, String password) {
        logger.info("Attempting authentication for user: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warning("User not found: " + username);
                    return new UnauthorizedException("Invalid username or password");
                });
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password,
                            Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
                    )
            );
            logger.info("Authentication successful for user: " + username);
            return jwtService.generateToken(authentication);
        } catch (BadCredentialsException e) {
            logger.warning("Invalid password for user: " + username);
            throw new UnauthorizedException("Invalid username or password");
        } catch (Exception e) {
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
        user.setPassword(securityPasswordEncoder.encode(password));
        user.setRole(userRole);
        userRepository.save(user);
        logger.info("User registered: " + username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
        String token = jwtService.generateToken(authentication);
        logger.info("Token generated for user: " + username);
        return token;
    }
}