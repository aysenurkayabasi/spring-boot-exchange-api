package com.openpayd.exchange.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@UtilityClass
public class DateTimeUtil {
    public static LocalDateTime convertTimestampToLocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString);
    }

}
