package com.example.migrosone.courierTracking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierEvent {
    private Long CourierId;
    private String storeName;
    private String eventName;
    private LocalDateTime time;
}
