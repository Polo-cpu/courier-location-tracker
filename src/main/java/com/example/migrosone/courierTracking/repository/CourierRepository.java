package com.example.migrosone.courierTracking.repository;

import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<CourierEntity, Long> {
    Optional<CourierEntity> findByCourierId(Long courierId);
}
