package com.example.migrosone.courierTracking.model.mapper;

import com.example.migrosone.courierTracking.model.dto.CourierEvent;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventEntity toEntity(CourierEvent courierEvent);
}
