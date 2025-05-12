package com.example.passenger.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import com.example.passenger.dto.LoginRequest;
import com.example.passenger.dto.PassengerProfileDto;
import com.example.passenger.dto.RegisterRequest;
import com.example.passenger.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private PassengerProfileDto profileDto;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("praveenpatiar2235@gmail.com");
        registerRequest.setFullName("Praveen Patidar");
        registerRequest.setUsername("Praveen7879");
        registerRequest.setPassword("1234567");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("Praveen7879");
        loginRequest.setPassword("1234567");

        profileDto = new PassengerProfileDto("Praveen7879", "praveenpatidar2235@gmail.com", "Praveen Patidar");
    }

    @Test
    @WithMockUser(roles = "ALLOW_REGISTER")
    void testRegisterUser() throws Exception {
        doNothing().when(authService).register(any(RegisterRequest.class));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        verify(authService, times(1)).register(any(RegisterRequest.class));
    }

    @Test
    void testLogin() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenReturn("jwt-token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt-token"));

        verify(authService, times(1)).login(any(LoginRequest.class));
    }

    @Test
    @WithMockUser(username = "Praveen7879")
    void testGetProfile() throws Exception {
        when(authService.getProfile()).thenReturn(profileDto);

        mockMvc.perform(get("/api/auth/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Praveen7879"))
                .andExpect(jsonPath("$.email").value("praveenpatidar2235@gmail.com"))
                .andExpect(jsonPath("$.fullName").value("Praveen Patidar"));

        verify(authService, times(1)).getProfile();
    }
}
