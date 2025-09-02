package com.example.migrosone.courierTracking.repository;

import com.example.migrosone.courierTracking.model.dto.CourierEvent;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findAllByCourierId(Long courierId);
    Optional<EventEntity> findFirstByCourierIdOrderByEventTimeDesc(Long courierId);
}
