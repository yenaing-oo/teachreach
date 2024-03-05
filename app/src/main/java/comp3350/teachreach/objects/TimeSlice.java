package comp3350.teachreach.objects;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    public Instant getStartTime() {
        return startTime;
    }

    public OffsetDateTime getStartODT() {
        return OffsetDateTime.ofInstant(
                startTime, ZoneId.systemDefault());
    }

    public OffsetDateTime getEndODT() {
        return OffsetDateTime.ofInstant(
                endTime, ZoneId.systemDefault());
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean conflictsWith(TimeSlice that) {
        return !(this.startTime.isAfter(that.endTime) || this.endTime.isBefore(that.startTime));
    }

    public boolean canContain(TimeSlice that) {
        return this.startTime.isBefore(that.startTime) && this.endTime.isAfter(that.endTime);
    }

    public void mergeWith(TimeSlice that) {
        if (this.startTime.isAfter(that.startTime)) {
            this.startTime = that.startTime;
        }
        if (this.endTime.isBefore(that.endTime)) {
            this.endTime = that.endTime;
        }
        this.duration = Duration.between(this.startTime, this.endTime);
    }
}
