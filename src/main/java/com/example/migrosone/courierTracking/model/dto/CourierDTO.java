package com.example.migrosone.courierTracking.model.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierDTO {
    private Long courierId;

    private String name;

    private String phoneNumber;

}
