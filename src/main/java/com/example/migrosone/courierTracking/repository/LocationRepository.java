package com.example.migrosone.courierTracking.repository;

import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findAllMovesByCourierId(Long courierId);
}
