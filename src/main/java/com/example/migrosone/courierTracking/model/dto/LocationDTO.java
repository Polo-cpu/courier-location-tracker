package com.example.migrosone.courierTracking.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();
}
