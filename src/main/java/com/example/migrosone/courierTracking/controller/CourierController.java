package com.example.migrosone.courierTracking.controller;

import com.example.migrosone.courierTracking.model.dto.CourierDTO;
import com.example.migrosone.courierTracking.model.dto.LocationDTO;
import com.example.migrosone.courierTracking.model.dummy.MessageCodes;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.entity.EventEntity;
import com.example.migrosone.courierTracking.model.entity.LocationEntity;
import com.example.migrosone.courierTracking.response.InternalApiResponse;
import com.example.migrosone.courierTracking.response.MessageResponse;
import com.example.migrosone.courierTracking.service.CourierService;
import com.example.migrosone.courierTracking.service.EventService;
import com.example.migrosone.courierTracking.service.LocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courier")
@AllArgsConstructor
@Slf4j
public class CourierController {
    private final CourierService courierService;
    private final LocationService locationService;
    private final EventService eventService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save/location")
    public InternalApiResponse<Optional<LocationEntity>> saveCourierLocation(@RequestBody LocationDTO locationDTO){
        Optional<LocationEntity> location = locationService.saveCourierLocation(locationDTO);
        eventService.checkAndPublishEntranceEvent(locationDTO.getCourierId(), locationDTO.getLat(), locationDTO.getLng());
        return InternalApiResponse.<Optional<LocationEntity>>builder()
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(location)
                .messageResponse(MessageResponse.builder()
                        .title(String.valueOf(MessageCodes.SUCCESS))
                        .description(String.valueOf(MessageCodes.LOCATION_SUCCESSFULLY_CREATED))
                        .build())
                .build();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public InternalApiResponse<Optional<CourierEntity>> createCourier(@RequestBody CourierDTO courierDTO){
        Optional<CourierEntity> entity = courierService.createCourier(courierDTO);
        return InternalApiResponse.<Optional<CourierEntity>>builder()
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(entity)
                .messageResponse(MessageResponse.builder()
                        .title(String.valueOf(MessageCodes.SUCCESS))
                        .description(String.valueOf(MessageCodes.COURIER_SUCCESSFULLY_CREATED))
                        .build())
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public InternalApiResponse<Optional<CourierEntity>> getCourierById(@PathVariable Long id) {
        Optional<CourierEntity> courier = courierService.findCourierById(id);
        return InternalApiResponse.<Optional<CourierEntity>>builder()
                .httpStatus(HttpStatus.OK)
                .payload(courier)
                .hasError(false)
                .messageResponse(MessageResponse.builder()
                        .title(String.valueOf(MessageCodes.SUCCESS))
                        .description(String.valueOf(MessageCodes.COURIER_SUCCESSFULLY_RETRIEVED))
                        .build())
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalDistance/{courierId}")
    public InternalApiResponse<Double> getDistanceByCourierId(@PathVariable("courierId") Long courierId){
        var distance = locationService.getTotalTravelledDistance(courierId);
        return InternalApiResponse.<Double>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(distance)
                .messageResponse(MessageResponse.builder()
                        .title(String.valueOf(MessageCodes.SUCCESS))
                        .description(String.valueOf(MessageCodes.TRAVELLED_DISTANCE_SUCCESSFULLY_CALCULATED))
                        .build())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events/{courierId}")
    public InternalApiResponse<List<EventEntity>> getEventsByCourierId(@PathVariable("courierId") Long courierId){
        List<EventEntity> events = eventService.getEventsByCourierId(courierId);
        return InternalApiResponse.<List<EventEntity>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(events)
                .messageResponse(MessageResponse.builder()
                        .title(String.valueOf(MessageCodes.SUCCESS))
                        .description(String.valueOf(MessageCodes.EVENTS_SUCCESSFULLY_FOUNDED))
                        .build())
                .build();
    }

}
