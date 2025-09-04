package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.exception.CourierNotFoundException;
import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.dummy.MessageCodes;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import com.example.migrosone.courierTracking.model.mapper.LocationMapper;
import com.example.migrosone.courierTracking.repository.CourierRepository;
import com.example.migrosone.courierTracking.repository.LocationRepository;
import com.example.migrosone.courierTracking.utils.GeoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CourierRepository courierRepository;
    private final LocationMapper locationMapper;
    private final GeoUtils geoUtils;

    public LocationService(LocationRepository locationRepository, CourierRepository courierRepository,
                           LocationMapper locationMapper, GeoUtils geoUtils) {
        this.locationRepository = locationRepository;
        this.courierRepository = courierRepository;
        this.locationMapper = locationMapper;
        this.geoUtils = geoUtils;
    }



    public Double getTotalTravelledDistance(Long courierId) {
        List<LocationEntity> locations = locationRepository.findAllMovesByCourierId(courierId);
        if (locations.isEmpty() || locations.size() < 2) {
            log.error("Not enough location data to calculate distance for courierId: {}", courierId);
            return 0.0;
        }
        Double totalDistance = 0.0;
        for (int i = 0; i < locations.size() - 1; i++) {
            LocationEntity fromDistance = locations.get(i);
            LocationEntity toDistance = locations.get(i + 1);

            totalDistance += geoUtils.calculateDistanceForKM(fromDistance.getLat(), fromDistance.getLng(),
                    toDistance.getLat(), toDistance.getLng());
        }
        log.info("Total travelled distance for courierId {}: {} km", courierId, totalDistance);
        return totalDistance;

    }
    @Transactional
    public Optional<LocationEntity> saveCourierLocation(LocationDTO locationDTO) {
        LocationEntity location = locationMapper.toEntity(locationDTO);
        Optional<CourierEntity> courier = courierRepository.findByCourierId(locationDTO.getCourierId());
        if (courier.isEmpty()) {
            throw new CourierNotFoundException(MessageCodes.COURIER_NOT_FOUND);
        }else {
            location.setCourier(courier.get());
            return Optional.of(locationRepository.save(location));
        }
    }
}
