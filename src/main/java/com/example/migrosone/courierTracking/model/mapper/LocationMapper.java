package com.example.migrosone.courierTracking.model.mapper;

import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "courier", ignore = true)
    LocationEntity toEntity(LocationDTO locationDTO);
}
