package com.example.migrosone.courierTracking.model.dummy;

public enum MessageCodes implements IMessageCodes {
    COURIER_NOT_CREATED(700),
    COURIER_NOT_FOUND(701),
    EVENT_NOT_FOUND(800),
    DISTANCE_NOT_FOUND(801);

    private final int value;
    MessageCodes(int value) {
        this.value = value;
    }

    @Override
    public Integer getMessage() {
        return value;
    }
}
