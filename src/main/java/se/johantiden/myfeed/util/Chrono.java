package se.johantiden.myfeed.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;

public class Chrono {
    public static boolean isOlderThan(Duration duration, Instant date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return date.plus(duration).isBefore(Instant.now());
    }

    public static Instant parse(String dateString, String format) {
        Instant instant = null;
        try {
            instant = new SimpleDateFormat(format).parse(dateString).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return instant;
    }
}
