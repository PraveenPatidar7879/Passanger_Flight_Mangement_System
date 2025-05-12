package com.example.passenger.service;

import com.example.passenger.dto.*;
import com.example.passenger.entity.Passenger;
import com.example.passenger.repository.PassengerRepository;
import com.example.passenger.security.JwtUtil;
import com.example.passenger.exception.ApiException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PassengerRepository repository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new ApiException("Username already exists", HttpStatus.BAD_REQUEST);
        }
        
        Passenger p = new Passenger();
        p.setUsername(request.getUsername());
        p.setPassword(encoder.encode(request.getPassword()));
        p.setEmail(request.getEmail());
        p.setFullName(request.getFullName());
        repository.save(p);
    }

    public String login(LoginRequest request) {
        Passenger p = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApiException("Invalid username", HttpStatus.UNAUTHORIZED));

        if (!encoder.matches(request.getPassword(), p.getPassword())) {
            throw new ApiException("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        return jwtUtil.generateToken(p.getUsername());
    }

    public PassengerProfileDto getProfile() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Passenger p = repository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        return new PassengerProfileDto(p.getUsername(), p.getEmail(), p.getFullName());
    }
}