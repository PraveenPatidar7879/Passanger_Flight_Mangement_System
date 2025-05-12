package com.example.common.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import java.util.stream.Collectors;

class FlightRequestMessageTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        FlightRequestMessage message = new FlightRequestMessage(
            "PASS123",
            "AA123",
            "BOOK",
            "PENDING"
        );

        var violations = validator.validate(message);
        assertTrue(violations.isEmpty(), "No violations should be found");
    }

    @Test
    void whenFlightNumberIsInvalid_thenViolationOccurs() {
        FlightRequestMessage message = new FlightRequestMessage(
            "PASS123",
            "123", // Invalid format
            "BOOK",
            "PENDING"
        );

        var violations = validator.validate(message);
        assertFalse(violations.isEmpty(), "Violations should be found");
        assertEquals(1, violations.size(), "Should have one violation");
        assertEquals("Flight number must be in format: XX123 or XX1234", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenRequestTypeIsInvalid_thenViolationOccurs() {
        FlightRequestMessage message = new FlightRequestMessage(
            "PASS123",
            "AA123",
            "INVALID", // Invalid request type
            "PENDING"
        );

        var violations = validator.validate(message);
        assertFalse(violations.isEmpty(), "Violations should be found");
        assertEquals(1, violations.size(), "Should have one violation");
        assertEquals("Request type must be either BOOK or CANCEL", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenRequiredFieldsAreMissing_thenViolationsOccur() {
        FlightRequestMessage message = new FlightRequestMessage(
            "",     // Empty passenger ID
            "",     // Empty flight number
            "",     // Empty request type
            null    // Status can be null
        );

        Set<ConstraintViolation<FlightRequestMessage>> violations = validator.validate(message);
        
        // We expect 5 violations:
        // 1. Passenger ID is blank
        // 2. Flight number is blank
        // 3. Flight number pattern doesn't match
        // 4. Request type is blank
        // 5. Request type pattern doesn't match
        assertEquals(5, violations.size(), "Should have five violations");
        
        Set<String> violationMessages = violations.stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toSet());
        
        assertTrue(violationMessages.contains("Passenger ID is required"), "Should have passenger ID violation");
        assertTrue(violationMessages.contains("Flight number is required"), "Should have flight number blank violation");
        assertTrue(violationMessages.contains("Flight number must be in format: XX123 or XX1234"), "Should have flight number format violation");
        assertTrue(violationMessages.contains("Request type is required"), "Should have request type blank violation");
        assertTrue(violationMessages.contains("Request type must be either BOOK or CANCEL"), "Should have request type format violation");
    }
} 