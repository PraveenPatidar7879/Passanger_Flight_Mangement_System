package com.example.passenger.kafka;

import com.example.common.dto.FlightRequestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PassengerKafkaProducer {
    
    private static final String TOPIC = "flight-requests";
    private static final Logger logger = LoggerFactory.getLogger(PassengerKafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, FlightRequestMessage> kafkaTemplate;

    public void sendFlightRequest(FlightRequestMessage message) {
        try {
            logger.info("Sending message to Kafka: {}", message);
            kafkaTemplate.send(TOPIC, message);
            logger.info("Message sent successfully");
        } catch (Exception e) {
            logger.error("Error sending message to Kafka: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
