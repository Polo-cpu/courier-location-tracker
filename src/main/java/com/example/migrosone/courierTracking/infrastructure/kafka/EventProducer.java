//package com.example.migrosone.courierTracking.infrastructure.kafka;
//
//import com.example.migrosone.courierTracking.model.dto.CourierEvent;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EventProducer {
//    private final KafkaTemplate<String, CourierEvent> kafkaTemplate;
//    private static final String TOPIC = "courier-events";
//    public void publishEvent(CourierEvent event) {
//        kafkaTemplate.send(TOPIC, event);
//    }
//}
//