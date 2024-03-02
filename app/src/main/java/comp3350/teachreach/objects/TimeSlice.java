package comp3350.teachreach.objects;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeSlice {
    private Instant startTime;
    private Instant endTime;
    private Duration duration;


    public TimeSlice(Instant startTime, Instant endTime, Duration duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public boolean conflictsWith(TimeSlice that) {
        return !(this.startTime.isAfter(that.endTime) || this.endTime.isBefore(that.startTime));
    }

    public static TimeSlice of(
            int year,
            int month,
            int day,
            int hour,
            int minute,
            int durationInMinutes) {
        Instant start = LocalDateTime
                .of(year, month, day, hour, minute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Duration duration = Duration.ofMinutes(durationInMinutes);
        return new TimeSlice(start, start.plus(duration), duration);
    }

    public static TimeSlice of(
            int startYear,
            int startMonth,
            int startDay,
            int startHour,
            int startMinute,
            int endYear,
            int endMonth,
            int endDay,
            int endHour,
            int endMinute) {
        Instant start = LocalDateTime
                .of(
                        startYear,
                        startMonth,
                        startDay,
                        startHour,
                        startMinute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant end = LocalDateTime
                .of(
                        endYear,
                        endMonth,
                        endDay,
                        endHour,
                        endMinute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Duration duration = Duration.between(start, end);

        return new TimeSlice(start, end, duration);
    }
}
