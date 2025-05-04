package com.example.common.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlightRequestMessage implements Serializable {
    private String requestType; // e.g., "GET_ALL_FLIGHTS"
    private String passengerId;

    // Constructors, getters, setters
}
