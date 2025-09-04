package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.exception.CourierNotCreatedException;
import com.example.migrosone.courierTracking.exception.CourierNotFoundException;
import com.example.migrosone.courierTracking.model.dto.CourierDTO;
import com.example.migrosone.courierTracking.model.dummy.MessageCodes;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.mapper.CourierMapper;
import com.example.migrosone.courierTracking.repository.CourierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CourierService {

    private final CourierRepository courierRepository;

    private final CourierMapper courierMapper;
    public CourierService(CourierRepository courierRepository, CourierMapper courierMapper) {
        this.courierRepository = courierRepository;
        this.courierMapper = courierMapper;
    }

    @Transactional
    public Optional<CourierEntity> createCourier(CourierDTO courierDTO) {
        try {
            CourierEntity courier = courierRepository.save(courierMapper.toEntity(courierDTO));
            log.info("Courier created with id: {}", courierDTO.getCourierId());
            return Optional.of(courier);
        }catch (Exception e){
            throw new CourierNotCreatedException(MessageCodes.COURIER_NOT_CREATED);
        }
    }

    public Optional<CourierEntity> findCourierById(Long id) {
        Optional<CourierEntity> courier = courierRepository.findById(id);
        if(courier.isEmpty()){
            throw new CourierNotFoundException(MessageCodes.COURIER_NOT_FOUND);
        }
        else{
            log.info("Courier found with id: {}", id);
            return courier;
        }
    }
}
