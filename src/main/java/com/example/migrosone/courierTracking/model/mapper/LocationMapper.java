package com.example.migrosone.courierTracking.model.mapper;

import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mappings({
            @Mapping(source = "courierId", target = "courier.courierId"),
            @Mapping(source = "lat", target = "lat"),
            @Mapping(source = "lng", target = "lng")
    })
    LocationEntity toEntity(LocationDTO locationDTO);
}
