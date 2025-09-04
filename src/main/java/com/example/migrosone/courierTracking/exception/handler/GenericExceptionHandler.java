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
                .messageResponse(MessageResponse.builder().title(MessageUtils.getMessage(MessageCodes.COURIER_NOT_CREATED))
                        .description(MessageUtils.getMessage(courierNotCreatedException.getMessageCodes()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessages(Collections.singletonList(courierNotCreatedException.getMessage()))
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourierNotFoundException.class)
    public InternalApiResponse<String> handleCourierNotFoundException(CourierNotFoundException courierNotFoundException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(MessageUtils.getMessage(MessageCodes.COURIER_NOT_FOUND))
                        .description(MessageUtils.getMessage(courierNotFoundException.getMessageCodes()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessages(Collections.singletonList(courierNotFoundException.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DistanceNotFoundException.class)
    public InternalApiResponse<String> handleDistanceNotFoundException(DistanceNotFoundException distanceNotFoundException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(MessageUtils.getMessage(MessageCodes.DISTANCE_NOT_FOUND))
                        .description(MessageUtils.getMessage(distanceNotFoundException.getMessageCodes()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessages(Collections.singletonList(distanceNotFoundException.getMessage()))
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EventNotFoundException.class)
    public InternalApiResponse<String> handleEventNotFoundException(EventNotFoundException eventNotFoundException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(MessageUtils.getMessage(MessageCodes.EVENT_NOT_FOUND))
                        .description(MessageUtils.getMessage(eventNotFoundException.getMessageCodes()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessages(Collections.singletonList(eventNotFoundException.getMessage()))
                .build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EventNotCreatedException.class)
    public InternalApiResponse<String> handleEventNotCreatedException(EventNotCreatedException eventNotCreatedException) {
        return InternalApiResponse.<String>builder()
                .messageResponse(MessageResponse.builder().title(MessageUtils.getMessage(MessageCodes.EVENT_NOT_CREATED))
                        .description(MessageUtils.getMessage(eventNotCreatedException.getMessageCodes()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessages(Collections.singletonList(eventNotCreatedException.getMessage()))
                .build();
    }
}
