package com.example.passenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerProfileDto {
    private String username;
    private String email;
    private String fullName;
}
