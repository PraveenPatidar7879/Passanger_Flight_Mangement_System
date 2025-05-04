package com.example.flight.controller;

import com.example.flight.dto.FlightRequest;
import com.example.flight.dto.FlightResponse;
import com.example.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/createflight")
    public FlightResponse createFlight(@RequestBody FlightRequest request) {
        return flightService.createFlight(request);
    }

    @GetMapping("/getallflight")
    public List<FlightResponse> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/getbyid/{id}")
    public FlightResponse getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }
}
