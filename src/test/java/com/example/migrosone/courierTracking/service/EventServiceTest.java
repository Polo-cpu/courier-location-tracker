package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.model.dto.CourierEvent;
import com.example.migrosone.courierTracking.model.dto.DummyLocationDTO;
import com.example.migrosone.courierTracking.model.dummy.EventTypeEnum;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import com.example.migrosone.courierTracking.model.mapper.EventMapper;
import com.example.migrosone.courierTracking.repository.EventRepository;
import com.example.migrosone.courierTracking.utils.DummyLocationLoader;
import com.example.migrosone.courierTracking.utils.GeoUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private GeoUtils geoUtils;

    @Mock
    private DummyLocationLoader dummyLocationLoader;

    @InjectMocks
    private EventService eventService;

    @Test
    void testCheckAndPublishEntranceEvent() {
        LocationEntity locationEntity = sampleLocationEntity();
        Long courierId = locationEntity.getCourier().getCourierId();
        DummyLocationDTO store = new DummyLocationDTO("Store A", 40.7128, -74.0060);
        List<DummyLocationDTO> storeLocations = Collections.singletonList(store);
        Mockito.when(dummyLocationLoader.getLocations()).thenReturn(storeLocations);
        Mockito.when(geoUtils.calculateDistanceForMeters(locationEntity.getLat(), locationEntity.getLng(),
                store.getLat(), store.getLng())).thenReturn(50.0);
        when(eventRepository.findFirstByCourierIdOrderByEventTimeDesc(courierId)).thenReturn(Optional.empty());

        EventEntity expectedEventEntity = new EventEntity();
        when(eventMapper.toEntity(Mockito.any(CourierEvent.class))).thenReturn(expectedEventEntity);

        eventService.checkAndPublishEntranceEvent(courierId, locationEntity.getLat(), locationEntity.getLng());
        Mockito.verify(eventRepository, Mockito.times(1)).save(expectedEventEntity);
        Mockito.verify(dummyLocationLoader, Mockito.times(1)).getLocations();
        Mockito.verify(geoUtils, Mockito.times(1)).calculateDistanceForMeters(
                locationEntity.getLat(), locationEntity.getLng(), store.getLat(), store.getLng());
    }

    public LocationEntity sampleLocationEntity(){
        return new LocationEntity(1L, LocalDateTime.of(2025, 6, 4, 0, 0, 0),sampleCourier(),40.7128,-74.0060);
    }
    public CourierEntity sampleCourier(){
        return new CourierEntity(1L, 100L, "John Doe","123123");
    }
}
