package com.example.migrosone.courierTracking.exception.handler;

import com.example.migrosone.courierTracking.exception.*;
import com.example.migrosone.courierTracking.model.dummy.MessageCodes;
import com.example.migrosone.courierTracking.response.InternalApiResponse;
import com.example.migrosone.courierTracking.response.MessageResponse;
import com.example.migrosone.courierTracking.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GenericExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CourierNotCreatedException.class)
    public InternalApiResponse<String> handleCourierNotCreatedException(CourierNotCreatedException courierNotCreatedException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(String.valueOf(MessageCodes.ERROR))
                        .description(String.valueOf(MessageCodes.COURIER_NOT_CREATED))
                        .build())
                .hasError(true)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourierNotFoundException.class)
    public InternalApiResponse<String> handleCourierNotFoundException(CourierNotFoundException courierNotFoundException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(String.valueOf(MessageCodes.ERROR))
                        .description(String.valueOf(MessageCodes.COURIER_NOT_FOUND))
                        .build())
                .hasError(true)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DistanceNotFoundException.class)
    public InternalApiResponse<String> handleDistanceNotFoundException(DistanceNotFoundException distanceNotFoundException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(String.valueOf(MessageCodes.ERROR))
                        .description(String.valueOf(MessageCodes.DISTANCE_NOT_FOUND))
                        .build())
                .hasError(true)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EventNotFoundException.class)
    public InternalApiResponse<String> handleEventNotFoundException(EventNotFoundException eventNotFoundException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(String.valueOf(MessageCodes.ERROR))
                        .description(String.valueOf(MessageCodes.EVENT_NOT_FOUND))
                        .build())
                .hasError(true)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EventNotCreatedException.class)
    public InternalApiResponse<String> handleEventNotCreatedException(EventNotCreatedException eventNotCreatedException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(String.valueOf(MessageCodes.ERROR))
                        .description(String.valueOf(MessageCodes.EVENT_NOT_CREATED))
                        .build())
                .hasError(true)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
