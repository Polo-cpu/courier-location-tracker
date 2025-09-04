package com.example.migrosone.courierTracking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MIG_COURIER_EVENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "COURIER_ID")
    private Long courierId;
    @Column(name = "EVENT_NAME")
    private String eventName;
    @Column(name = "EVENT_TIME")
    private LocalDateTime eventTime = LocalDateTime.now();
    @Column(name = "STORE_NAME")
    private String storeName;

}
