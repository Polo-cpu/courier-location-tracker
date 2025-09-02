package com.example.migrosone.courierTracking.infrastructure.kafka;

import com.example.migrosone.courierTracking.model.dto.CourierEvent;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import com.example.migrosone.courierTracking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EventConsumer {
//    private final EventRepository eventRepository;
//
//    @KafkaListener(topics = "courier-events", groupId = "courier-group" )
//    public void consume(CourierEvent event) {
//        EventEntity eventEntity = new EventEntity();
//        eventEntity.setCourierId(event.getCourierId());
//        eventEntity.setEventName(event.getEventName());
//        eventEntity.setEventTime(event.getTime());
//
//
//    }
//
//
//}
//