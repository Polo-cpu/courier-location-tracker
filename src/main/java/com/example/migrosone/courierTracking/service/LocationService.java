package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import com.example.migrosone.courierTracking.model.mapper.LocationMapper;
import com.example.migrosone.courierTracking.repository.CourierRepository;
import com.example.migrosone.courierTracking.repository.LocationRepository;
import com.example.migrosone.courierTracking.utils.DummyLocationLoader;
import com.example.migrosone.courierTracking.utils.GeoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    private final CourierRepository courierRepository;
    private final LocationMapper locationMapper;
    private final GeoUtils geoUtils;
    private final DummyLocationLoader dummyLocationLoader;

    public LocationService(LocationRepository locationRepository, CourierRepository courierRepository, LocationMapper locationMapper, GeoUtils geoUtils,
                           DummyLocationLoader dummyLocationLoader) {
        this.locationRepository = locationRepository;
        this.courierRepository = courierRepository;
        this.locationMapper = locationMapper;
        this.geoUtils = geoUtils;
        this.dummyLocationLoader = dummyLocationLoader;
    }



    public Double getTotalTravelledDistance(Long courierId) {
        List<LocationEntity> locations = locationRepository.findAllMovesByCourierId(courierId);
        if (locations.isEmpty() || locations.size() < 2) {
            return 0.0;
        }
        Double totalDistance = 0.0;
        for (int i = 0; i < locations.size() - 1; i++) {
            LocationEntity fromDistance = locations.get(i);
            LocationEntity toDistance = locations.get(i + 1);

            totalDistance += geoUtils.calculateDistanceForMeters(fromDistance.getLat(), fromDistance.getLng(),
                    toDistance.getLat(), toDistance.getLng());
        }
        return totalDistance;

    }
    @Transactional
    public Optional<LocationEntity> saveCourierLocation(LocationDTO locationDTO) {
        LocationEntity location = locationMapper.toEntity(locationDTO);
        CourierEntity courier = courierRepository.findById(locationDTO.getCourierId())
                .orElseThrow(() -> new IllegalArgumentException("Courier not found with id: "
                        + locationDTO.getCourierId()));
        location.setCourier(courier);
        return Optional.of(locationRepository.save(location));
    }
}
