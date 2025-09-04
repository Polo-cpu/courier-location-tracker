package com.example.migrosone.courierTracking.model.dto;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Getter
@Setter
@RequiredArgsConstructor
public class LocationDTO {

    @Nullable
    private LocalDateTime time;

    private Long courierId;

    private Double lat;

    private Double lng;
}
