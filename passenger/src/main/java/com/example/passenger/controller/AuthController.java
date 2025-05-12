package com.example.passenger.controller;

import com.example.passenger.dto.*;
import com.example.passenger.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        service.register(request);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @GetMapping("/profile")
    public PassengerProfileDto profile() {


        return service.getProfile();
    }

}