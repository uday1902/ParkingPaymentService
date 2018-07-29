package devcraft.parking.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static boolean isLessThanAnHour(Duration timeInParking) {
        return timeInParking.toMillis() < Duration.ofMinutes(60).toMillis();
    }

    public static boolean isLessThan10Min(Duration timeInParking) {
        return timeInParking.toMillis() < Duration.ofMinutes(10).toMillis();
    }

    public static long minAsMillis(int min) {
        return min * 60 * 1000L;
    }

    public static long parseTimeToMillis(String time) {
        Instant localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
        return localDateTime.toEpochMilli();
    }

    public static Instant parseTimeToInstant(String time) {
        Instant localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
        return localDateTime;
    }
}
