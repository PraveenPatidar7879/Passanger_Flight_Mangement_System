package com.example.passenger.service;

import com.example.passenger.dto.*;
import com.example.passenger.entity.Passenger;
import com.example.passenger.repository.PassengerRepository;
import com.example.passenger.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PassengerRepository repository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        Passenger p = new Passenger();
        p.setUsername(request.getUsername());
        p.setPassword(encoder.encode(request.getPassword()));
        p.setEmail(request.getEmail());
        p.setFullName(request.getFullName());
        repository.save(p);
    }

    public String login(LoginRequest request) {
        Passenger p = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid UserName"));

        if (!encoder.matches(request.getPassword(), p.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(p.getUsername());
    }

   
    public PassengerProfileDto getProfile() {
        // 1) get username from the SecurityContext (populated by JwtAuthFilter)
        String username = SecurityContextHolder.getContext()
                              .getAuthentication()
                              .getName();

        // 2) load the Passenger
        Passenger p = repository.findByUsername(username)
                         .orElseThrow(() -> new RuntimeException("User not found"));

        // 3) map to DTO
        return new PassengerProfileDto(p.getUsername(), p.getEmail(), p.getFullName());
    }
}