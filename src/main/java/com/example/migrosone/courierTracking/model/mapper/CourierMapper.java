package com.example.migrosone.courierTracking.model.mapper;

import com.example.migrosone.courierTracking.model.dto.CourierDTO;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface CourierMapper {

    CourierEntity toEntity(CourierDTO courierDTO);
}
