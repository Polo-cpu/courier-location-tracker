package com.example.migrosone.courierTracking.utils;

import com.example.migrosone.courierTracking.model.dummy.IMessageCodes;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass
public class MessageUtils {
    private static final String RESOURCE_BUNDLE_NAME = "ApplicationMessages";

    public static String getMessage(IMessageCodes messageCode) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
        String messageKey = String.valueOf(messageCode.getMessage());
        try {
            return resourceBundle.getString(messageKey);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
