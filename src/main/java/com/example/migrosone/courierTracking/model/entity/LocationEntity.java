package com.example.migrosone.courierTracking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MIG_COURIER_LOCATION")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIME")
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "COURIER_ID")
    private CourierEntity courier;

    @Column(name = "LATITUDE")
    private Double lat;

    @Column(name = "LONGITUDE")
    private Double lng;

    @PrePersist
    public void prePersist() {
        if (time == null) {
            time = LocalDateTime.now();
        }
    }


}
