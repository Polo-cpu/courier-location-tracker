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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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

        Mockito.when(locationRepository.findByCourier_CourierId(courier.getCourierId()))
                .thenReturn(locations);
        Mockito.when(geoUtils.calculateDistanceForKM(locations.get(0).getLat(), locations.get(0).getLng(),
                locations.get(1).getLat(), locations.get(1).getLng()))
                .thenReturn(1000.0);
        Double totalDistance = locationService.getTotalTravelledDistance(courier.getCourierId());
        Assertions.assertEquals(1000.0, totalDistance);

    }

    @Test
    void testGetTotalTravelledDistance_EmptyList() {
        Mockito.when(locationRepository.findByCourier_CourierId(Mockito.anyLong()))
                .thenReturn(List.of());
        Double totalDistance = locationService.getTotalTravelledDistance(1L);
        Assertions.assertEquals(0.0, totalDistance);
    }

    @Test
    void testSaveCourierLocation_Success() {
        // Arrange
        Long courierId = 1L;
        double latitude = 40.7128;
        double longitude = -74.0060;

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setCourierId(courierId);
        locationDTO.setLat(latitude);
        locationDTO.setLng(longitude);

        CourierEntity courier = new CourierEntity();
        courier.setCourierId(courierId);

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLat(latitude);
        locationEntity.setLng(longitude);
        locationEntity.setCourier(courier);

        Mockito.when(courierRepository.findByCourierId(eq(courierId))).thenReturn(Optional.of(courier));
        Mockito.when(locationMapper.toEntity(any(LocationDTO.class))).thenReturn(locationEntity);
        Mockito.when(locationRepository.save(any(LocationEntity.class))).thenReturn(locationEntity);

        // Act
        Optional<LocationEntity> result = locationService.saveCourierLocation(locationDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(latitude, result.get().getLat());
        Assertions.assertEquals(longitude, result.get().getLng());
        Assertions.assertEquals(courier, result.get().getCourier());
        Mockito.verify(courierRepository, Mockito.times(1)).findByCourierId(courierId);
        Mockito.verify(locationRepository, Mockito.times(1)).save(locationEntity);
    }

    @Test
    void testSaveCourierLocation_Error() {
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
