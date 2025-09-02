package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.model.dto.CourierEvent;
import com.example.migrosone.courierTracking.model.dto.DummyLocationDTO;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import com.example.migrosone.courierTracking.model.mapper.EventMapper;
import com.example.migrosone.courierTracking.repository.EventRepository;
import com.example.migrosone.courierTracking.utils.DummyLocationLoader;
import com.example.migrosone.courierTracking.utils.GeoUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.example.migrosone.courierTracking.model.dummy.EventTypeEnum;


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
            Double distance = geoUtils.calculateDistanceForMeters(lat, lng, store.getLat(), store.getLng());

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
                        return;
                    }
                }
                eventRepository.save(eventMapper.toEntity(newEvent));
                break;
            }
        }
    }
}
   // public void checkAndPublishReEntranceEvent(UUID courierId, Double lat, Double lng){
   //     List<DummyLocationDTO> storeLocations = this.dummyLocationLoader.getLocations();
   //
   //     for (DummyLocationDTO store : storeLocations){
   //         Double distance = geoUtils.calculateDistanceForMeters(lat, lng, store.getLat(), store.getLng());
   //
   //         if(distance<=DISTANCE_THRESHOLD_METERS) {
   //             CourierEvent event = new CourierEvent(courierId, "ENTRANCE", LocalDateTime.now());
   //             EventEntity entity = eventMapper.toEntity(event);
   //             eventRepository.save(entity);
   //             break;
   //         }
   //     }
   // }
   // public void checkAndPublishCourierCreatedEvent(UUID courierId){
   //     CourierEvent event = new CourierEvent(courierId, "COURIER_CREATED", LocalDateTime.now());
   //     EventEntity entity = eventMapper.toEntity(event);
   //     eventRepository.save(entity);
   // }
   // public List<EventEntity> getAllEventsByCourierId(UUID courierId){
   //     return eventRepository.findAll();
   // }
