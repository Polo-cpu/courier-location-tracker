package com.example.migrosone.courierTracking.controller;

import com.example.migrosone.courierTracking.model.dto.CourierDTO;
import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import com.example.migrosone.courierTracking.service.CourierService;
import com.example.migrosone.courierTracking.service.EventService;
import com.example.migrosone.courierTracking.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courier")
@AllArgsConstructor
public class CourierController {
    private final CourierService courierService;
    private final LocationService locationService;
    private final EventService eventService;
    @PostMapping("/save/location")
    public ResponseEntity<LocationEntity> saveCourierLocation(@RequestBody LocationDTO locationDTO){
        Optional<LocationEntity> location = locationService.saveCourierLocation(locationDTO);
        eventService.checkAndPublishEntranceEvent(locationDTO.getCourierId(), locationDTO.getLat(), locationDTO.getLng());
        return location.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/create")
    public ResponseEntity<CourierEntity> createCourier(@RequestBody CourierDTO courierDTO){
        Optional<CourierEntity> entity = courierService.createCourier(courierDTO);
        return entity.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
    //@GetMapping("/all")
    //public ResponseEntity<List<CourierEntity>> getAllCurrentCouriers(){
    //    List<CourierEntity> couriers = courierService.getAllByCourierId();
    //    return new ResponseEntity<>(couriers, HttpStatus.OK);
    //}

    @GetMapping("/{id}")
    public ResponseEntity<CourierEntity> getCourierById(@PathVariable Long id) {
        Optional<CourierEntity> courier = courierService.findCourierById(id);
        assert courier.orElse(null) != null;
        return new ResponseEntity<>(courier.orElse(null), HttpStatus.OK);
    }

    @GetMapping("/totalDistance/{id}")
    public ResponseEntity<Double> getDistanceByCourierId(@PathVariable("id") Long id){
        Double distance = locationService.getTotalTravelledDistance(id);
        return new ResponseEntity<>(distance, HttpStatus.OK);
    }

}
