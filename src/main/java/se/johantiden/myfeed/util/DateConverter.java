package se.johantiden.myfeed.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class DateConverter {
    public static Date toDate(Instant instant) {
        return instant != null ? Date.from(instant) : null;
    }

    public static Instant toInstant(Date date) {
        return date != null ? date.toInstant() : null;
    }

    public static Instant toInstant(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant() : null;
    }

    public static Instant toInstant(Long timestamp) {
        return timestamp != null ? Instant.ofEpochMilli(timestamp) : null;
    }

    public static Long toEpoch(Instant instant) {
        return instant != null ? instant.toEpochMilli() : null;
    }

    public static Timestamp toSqlTimestamp(Instant instant) {
        return instant != null ? new Timestamp(toEpoch(instant)) : null;
    }


}
