package com.example.migrosone.courierTracking.model.mapper;

import com.example.migrosone.courierTracking.model.dto.CourierDTO;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring")
public interface CourierMapper {

    CourierEntity toEntity(CourierDTO courierDTO);
}
