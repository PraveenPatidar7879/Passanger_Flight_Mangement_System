package com.example.common.dto;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequestMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Passenger ID is required")
    @JsonProperty("passenger_id")
    private String passengerId;

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2}\\d{3,4}$", message = "Flight number must be in format: XX123 or XX1234")
    @JsonProperty("flight_number")
    private String flightNumber;

    @NotBlank(message = "Request type is required")
    @Pattern(regexp = "^(BOOK|CANCEL)$", message = "Request type must be either BOOK or CANCEL")
    @JsonProperty("request_type")
    private String requestType;

    @JsonProperty("status")
    private String status;

    // Constructors, getters, setters
}
