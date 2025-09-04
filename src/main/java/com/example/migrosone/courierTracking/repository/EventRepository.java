package com.example.migrosone.courierTracking.repository;

import com.example.migrosone.courierTracking.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByCourierId(Long courierId);
    Optional<EventEntity> findFirstByCourierIdOrderByEventTimeDesc(Long courierId);
}
