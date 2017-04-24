package se.johantiden.myfeed.util;

import java.time.Duration;
import java.time.Instant;

public class Chrono {
    public static boolean isOlderThan(Duration duration, Instant date) {
        return date.plus(duration).isBefore(Instant.now());
    }
}
