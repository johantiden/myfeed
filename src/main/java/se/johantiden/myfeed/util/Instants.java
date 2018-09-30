package se.johantiden.myfeed.util;

import java.time.Instant;

public class Instants {
    public static Instant max(Instant a, Instant b) {
        return a.compareTo(b) > 0 ? a : b;
    }
}
