package com.example.migrosone.courierTracking.exception;

import com.example.migrosone.courierTracking.model.dummy.IMessageCodes;
import com.example.migrosone.courierTracking.utils.MessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CourierNotFoundException extends RuntimeException {
    private final IMessageCodes messageCodes;
    public CourierNotFoundException(IMessageCodes messageCodes) {
        super(MessageUtils.getMessage(messageCodes));
        this.messageCodes = messageCodes;
        log.error("[CourierNotFoundException} -> message: {} - developer message: {}",
                MessageUtils.getMessage(messageCodes), messageCodes);
    }
}
