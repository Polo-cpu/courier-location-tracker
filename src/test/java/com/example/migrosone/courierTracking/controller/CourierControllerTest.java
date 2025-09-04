package com.example.migrosone.courierTracking.controller;

import com.example.migrosone.courierTracking.exception.handler.GenericExceptionHandler;
import com.example.migrosone.courierTracking.model.dto.CourierDTO;
import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import com.example.migrosone.courierTracking.service.CourierService;
import com.example.migrosone.courierTracking.service.EventService;
import com.example.migrosone.courierTracking.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CourierControllerTest {


    private MockMvc mockMvc;

    @Mock
    private CourierService courierService;

    @Mock
    private LocationService locationService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private CourierController courierController;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(courierController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void testCreateCourier() throws Exception {
        CourierDTO courierDTO = new CourierDTO();
        courierDTO.setCourierId(1L);

        CourierEntity courierEntity = new CourierEntity();
        courierEntity.setId(1L);

        Mockito.when(courierService.createCourier(any(CourierDTO.class))).thenReturn(Optional.of(courierEntity));

        mockMvc.perform(post("/api/courier/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courierDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.payload.id").value(1))
                .andExpect(jsonPath("$.hasError").value(false));

        Mockito.verify(courierService, Mockito.times(1)).createCourier(any(CourierDTO.class));
    }

    @Test
    void testGetCourierById() throws Exception {
        Long courierId = 1L;
        CourierEntity courierEntity = new CourierEntity();
        courierEntity.setId(courierId);

        Mockito.when(courierService.findCourierById(eq(courierId))).thenReturn(Optional.of(courierEntity));

        mockMvc.perform(get("/api/courier/{id}", courierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.id").value(courierId))
                .andExpect(jsonPath("$.hasError").value(false));
    }

    @Test
    void testSaveCourierLocation() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setCourierId(100L);
        locationDTO.setLat(40.0);
        locationDTO.setLng(30.0);

        System.out.println(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(locationDTO));

        mockMvc.perform(post("/api/courier/save/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(locationDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetDistanceByCourierId() throws Exception {
        Long courierId = 1L;
        double distance = 100.5;

        Mockito.when(locationService.getTotalTravelledDistance(eq(courierId))).thenReturn(distance);

        mockMvc.perform(get("/api/courier/totalDistance/{id}", courierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").value(distance))
                .andExpect(jsonPath("$.hasError").value(false));
    }
    @Test
    void getEventsByCourierId_EntranceOnly() throws Exception {
        Long courierId = 1L;

        EventEntity event1 = new EventEntity();
        event1.setId(1L);
        event1.setCourierId(courierId);
        event1.setEventName("ENTRANCE");
        event1.setEventTime(LocalDateTime.now());

        List<EventEntity> events = List.of(event1);

        Mockito.when(eventService.getEventsByCourierId(eq(courierId))).thenReturn(events);

        mockMvc.perform(get("/api/courier/events/{courierId}", courierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload[0].id").value(event1.getId()))
                .andExpect(jsonPath("$.payload[0].eventName").value("ENTRANCE"))
                .andExpect(jsonPath("$.hasError").value(false));

        Mockito.verify(eventService, Mockito.times(1)).getEventsByCourierId(eq(courierId));
    }
}