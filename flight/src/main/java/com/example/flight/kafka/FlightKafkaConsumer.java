 package com.example.flight.kafka;

 import com.example.common.dto.FlightRequestMessage;
 import org.springframework.kafka.annotation.KafkaListener;
 import org.springframework.stereotype.Service;

 @Service
 public class FlightKafkaConsumer {

     @KafkaListener(topics = "flight-requests", groupId = "flight-service-group")
     public void consumeFlightRequest(FlightRequestMessage message) {
        // Process the message
        System.out.println("Received flight request: " + message);
     }
 }
