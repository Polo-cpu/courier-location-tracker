package com.example.migrosone.courierTracking.service;

import com.example.migrosone.courierTracking.exception.CourierNotFoundException;
import com.example.migrosone.courierTracking.model.dto.CourierDTO;
import com.example.migrosone.courierTracking.model.entity.CourierEntity;
import com.example.migrosone.courierTracking.model.mapper.CourierMapper;
import com.example.migrosone.courierTracking.repository.CourierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {
    @Mock
    private CourierMapper courierMapper;

    @Mock
    private CourierRepository courierRepository;

    @InjectMocks
    private CourierService courierService;


    @Test
    void testCreateCourier() {
        CourierEntity expectedCourier = sampleCourierList().get(0);
        Mockito.when(courierRepository.save(Mockito.any())).thenReturn(expectedCourier);
        CourierDTO courierDTO = new CourierDTO();
        courierDTO.setCourierId(expectedCourier.getCourierId());
        courierDTO.setName(expectedCourier.getName());
        courierDTO.setPhoneNumber(expectedCourier.getPhoneNumber());
        courierService.createCourier(courierDTO);
        verify(courierRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testFindCourierById(){
        CourierEntity expectedCourier = sampleCourierList().get(1);
        Optional<CourierEntity> optionalCourier = Optional.of(expectedCourier);

        Mockito.when(courierRepository.findById(Mockito.any())).thenReturn(optionalCourier);

        Optional<CourierEntity> actualCourier = courierService.findCourierById(1L);

        assertTrue(actualCourier.isPresent());
        Assertions.assertEquals(expectedCourier.getCourierId(), actualCourier.get().getCourierId());
        Assertions.assertEquals(expectedCourier.getName(), actualCourier.get().getName());
        Assertions.assertEquals(expectedCourier.getPhoneNumber(), actualCourier.get().getPhoneNumber());
        verify(courierRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    void testFindCourierById_NotFound(){
        Mockito.when(courierRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CourierNotFoundException.class, () -> {
            courierService.findCourierById(999L);
        });

        verify(courierRepository, Mockito.times(1)).findById(999L);
    }

    public List<CourierEntity> sampleCourierList() {
        List<CourierEntity> sampleCouriers = new ArrayList<>();
        sampleCouriers.add(new CourierEntity(1L, 100L, "courier1", "123123123"));
        sampleCouriers.add(new CourierEntity(2L, 101L, "courier2", "234234234"));
        sampleCouriers.add(new CourierEntity(3L, 102L, "courier3", "345345345"));
        return sampleCouriers;
    }
}
