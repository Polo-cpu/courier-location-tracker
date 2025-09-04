package com.example.migrosone.courierTracking.model.dto;

import lombok.*;


@Data
@Getter
@Setter
@RequiredArgsConstructor
public class LocationDTO {
    private Long courierId;

    private Double lat;

    private Double lng;
}
