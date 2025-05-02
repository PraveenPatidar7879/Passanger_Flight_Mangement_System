package com.example.flight.repository;

import com.example.flight.entity.Flight;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    // You can define custom queries if needed, for example:
     List<Flight> findByOriginAndDestination(String origin, String destination);
}
