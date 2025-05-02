package com.example.flight.service;

import com.example.flight.dto.FlightRequest;
import com.example.flight.dto.FlightResponse;
import com.example.flight.entity.Flight;
import com.example.flight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public FlightResponse createFlight(FlightRequest request) {
        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        flight.setOrigin(request.getOrigin());
        flight.setDestination(request.getDestination());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        System.out.println("Flight created: " + flight.getFlightNumber());
        Flight savedFlight = flightRepository.save(flight);
        System.out.println("Saved flight: " + savedFlight.getId());
        return new FlightResponse(savedFlight.getId(), savedFlight.getFlightNumber(), savedFlight.getOrigin(),
                savedFlight.getDestination(), savedFlight.getDepartureTime(), savedFlight.getArrivalTime());
    }

    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(flight -> new FlightResponse(flight.getId(), flight.getFlightNumber(), flight.getOrigin(),
                        flight.getDestination(), flight.getDepartureTime(), flight.getArrivalTime()))
                .collect(Collectors.toList());
    }

    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        
        return new FlightResponse(flight.getId(), flight.getFlightNumber(), flight.getOrigin(),
                flight.getDestination(), flight.getDepartureTime(), flight.getArrivalTime());
    }
}
