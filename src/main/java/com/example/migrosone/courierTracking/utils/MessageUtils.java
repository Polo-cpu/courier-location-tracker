package com.example.migrosone.courierTracking.utils;

import com.example.migrosone.courierTracking.model.dummy.IMessageCodes;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass
public class MessageUtils {
    private static final String RESOURCE_BUNDLE_NAME = "messages";
    private static final String SPECIAL_CHARACTER = "__";
    public static String getMessage(IMessageCodes messageCodes) {

        String messageKey = null;
        try {
            var resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
            messageKey = messageCodes.getClass().getSimpleName() + SPECIAL_CHARACTER + messageCodes;
            return resourceBundle.getString(messageKey);
        } catch (MissingResourceException missingResourceException) {
            log.error("message not found for the key: {} ", messageKey);
            return null;
        }
    }
}
