package com.example.migrosone.courierTracking.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class LocationDTO {
    private Long courierId;

    private Double lat;

    private Double lng;

    private LocalDateTime time = LocalDateTime.now();
}
