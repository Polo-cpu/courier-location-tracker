package com.example.migrosone.courierTracking.utils;

import com.example.migrosone.courierTracking.model.dto.DummyLocationDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;
@Component
@Getter
public class DummyLocationLoader {

    private final List<DummyLocationDTO> locations;

    public DummyLocationLoader(ObjectMapper objectMapper){
        try (InputStream inputStream = getClass().getResourceAsStream("/stores/stores.json")) {
            this.locations = objectMapper.readValue(inputStream, new TypeReference<List<DummyLocationDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load dummy locations", e);
        }
    }
}
