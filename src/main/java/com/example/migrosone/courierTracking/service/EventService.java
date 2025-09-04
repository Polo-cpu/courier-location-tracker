package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.exception.EventNotFoundException;
import com.example.migrosone.courierTracking.model.dto.CourierEvent;
import com.example.migrosone.courierTracking.model.dto.DummyLocationDTO;
import com.example.migrosone.courierTracking.model.dummy.MessageCodes;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import com.example.migrosone.courierTracking.model.mapper.EventMapper;
import com.example.migrosone.courierTracking.repository.EventRepository;
import com.example.migrosone.courierTracking.utils.DummyLocationLoader;
import com.example.migrosone.courierTracking.utils.GeoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.example.migrosone.courierTracking.model.dummy.EventTypeEnum;

@Slf4j
@Service
@RequiredArgsConstructor

public class EventService {

    private final GeoUtils geoUtils;
    private final EventRepository eventRepository;
    private final DummyLocationLoader dummyLocationLoader;
    @Autowired
    private final EventMapper eventMapper;
    private static final Double DISTANCE_THRESHOLD_METERS = 100.0;

    public void checkAndPublishEntranceEvent(Long courierId, Double lat, Double lng) {

        List<DummyLocationDTO> storeLocations = this.dummyLocationLoader.getLocations();
        for (DummyLocationDTO store : storeLocations) {
            Double distance = geoUtils.calculateDistanceForKM(lat, lng, store.getLat(), store.getLng());
            if (distance <= DISTANCE_THRESHOLD_METERS) {
                Optional<EventEntity> latestEventOpt = eventRepository.findFirstByCourierIdOrderByEventTimeDesc(courierId);
                CourierEvent newEvent = new CourierEvent(
                        courierId,
                        store.getName(),
                        EventTypeEnum.ENTRANCE.name(),
                        LocalDateTime.now()
                );
                if (latestEventOpt.isPresent()) {
                    LocalDateTime latestAllowedTime = latestEventOpt.get().getEventTime().plusMinutes(1);

                    if (!newEvent.getTime().isAfter(latestAllowedTime)) {
                        log.info("Duplicate entrance detected in 1 minute for courierId: {} at store: {}. Event not published.",
                                courierId, store.getName());
                        return;
                    }
                }
                eventRepository.save(eventMapper.toEntity(newEvent));
                log.info("Entrance event published for courierId: {} at store: {}", courierId, store.getName());
                break;
            }
        }
    }
    public List<EventEntity> getEventsByCourierId(Long courierId) {
        List<EventEntity> events = eventRepository.findByCourierId(courierId);
        if(events.isEmpty()){
            throw new EventNotFoundException(MessageCodes.EVENT_NOT_FOUND);
        }
        else{
            log.info("Events found with courierId: {}", courierId);
            return eventRepository.findByCourierId(courierId);
        }
    }
}