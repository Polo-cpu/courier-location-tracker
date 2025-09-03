package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.exception.CourierNotFoundException;
import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import com.example.migrosone.courierTracking.model.mapper.LocationMapper;
import com.example.migrosone.courierTracking.repository.CourierRepository;
import com.example.migrosone.courierTracking.repository.LocationRepository;
import com.example.migrosone.courierTracking.utils.GeoUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private LocationMapper locationMapper;

    @Mock
    private GeoUtils geoUtils;

    @InjectMocks
    private LocationService locationService;

    @Test
    void testGetTotalTravelledDistance() {
        List<LocationEntity> locations = sampleLocationList();
        CourierEntity courier = locations.get(0).getCourier();

        Mockito.when(locationRepository.findAllMovesByCourierId(courier.getCourierId()))
                .thenReturn(locations);
        Mockito.when(geoUtils.calculateDistanceForMeters(locations.get(0).getLat(), locations.get(0).getLng(),
                locations.get(1).getLat(), locations.get(1).getLng()))
                .thenReturn(1000.0);
        Double totalDistance = locationService.getTotalTravelledDistance(courier.getCourierId());
        Assertions.assertEquals(1000.0, totalDistance);

    }

    @Test
    void testGetTotalTravelledDistance_EmptyList() {
        Mockito.when(locationRepository.findAllMovesByCourierId(Mockito.anyLong()))
                .thenReturn(List.of());
        Double totalDistance = locationService.getTotalTravelledDistance(1L);
        Assertions.assertEquals(0.0, totalDistance);
    }

    @Test
    void testSaveCourierLocation() {
        // Arrange
        CourierEntity courier = new CourierEntity(1L, 100L, "John Doe", "1234567890");
        LocationEntity expectedLocation = new LocationEntity(null, LocalDateTime.now(), courier, 40.7128, -74.0060);

        // Mock the CourierRepository to return the courier
        Mockito.when(courierRepository.findByCourierId(courier.getCourierId()))
                .thenReturn(Optional.of(courier));

        // Mock the LocationRepository to save the location
        Mockito.when(locationRepository.save(Mockito.any())).thenAnswer(invocation -> {
            LocationEntity location = invocation.getArgument(0);
            location.setId(1L); // Simulate the database assigning an ID
            return location;
        });

        // Create a LocationDTO with the same courierId
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setCourierId(courier.getCourierId());
        locationDTO.setLat(expectedLocation.getLat());
        locationDTO.setLng(expectedLocation.getLng());

        // Act
        Optional<LocationEntity> savedLocation = locationService.saveCourierLocation(locationDTO);

        // Assert
        Assertions.assertTrue(savedLocation.isPresent());
        Assertions.assertEquals(expectedLocation.getLat(), savedLocation.get().getLat());
        Assertions.assertEquals(expectedLocation.getLng(), savedLocation.get().getLng());
        Assertions.assertEquals(courier, savedLocation.get().getCourier());
        Mockito.verify(courierRepository, Mockito.times(1)).findByCourierId(courier.getCourierId());
        Mockito.verify(locationRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testSaveCourierLocation_CourierNotFound() {
        // Arrange
        Mockito.when(courierRepository.findByCourierId(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setCourierId(999L);
        locationDTO.setLat(40.7128);
        locationDTO.setLng(-74.0060);

        // Act & Assert
        Assertions.assertThrows(CourierNotFoundException.class, () -> {
            locationService.saveCourierLocation(locationDTO);
        });

        Mockito.verify(courierRepository, Mockito.times(1)).findByCourierId(999L);
        Mockito.verify(locationRepository, Mockito.never()).save(Mockito.any());
    }
    public List<LocationEntity> sampleLocationList() {
        CourierEntity courier = new CourierEntity(2L, 100L,"John Doe", "1234567890");
        LocationEntity location1 = new LocationEntity(1L, LocalDateTime.now(), courier,40.7128, -74.0060);
        LocationEntity location2 = new LocationEntity(2L, LocalDateTime.now().plusMinutes(10),
                courier,40.7138, -74.0050);

        return List.of(location1, location2);
    }
}
