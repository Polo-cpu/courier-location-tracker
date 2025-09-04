package com.example.migrosone.courierTracking.model.dummy;

public enum MessageCodes implements IMessageCodes {
    COURIER_NOT_CREATED(700),
    COURIER_NOT_FOUND(701),
    EVENT_NOT_FOUND(800),
    EVENT_NOT_CREATED(801),
    DISTANCE_NOT_FOUND(901),
    SUCCESS(200),

    ERROR(400),

    LOCATION_SUCCESSFULLY_CREATED(100),

    COURIER_SUCCESSFULLY_CREATED(101),

    TRAVELLED_DISTANCE_SUCCESSFULLY_CALCULATED(102),

    EVENT_SUCCESSFULLY_CREATED(103),

    COURIER_SUCCESSFULLY_RETRIEVED(104);


    private final int value;
    MessageCodes(int value) {
        this.value = value;
    }


    @Override
    public Integer getMessage() {
        return value;
    }
}
