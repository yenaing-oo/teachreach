package comp3350.teachreach.objects;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

public class TimeSlice {
    private Instant startTime;
    private Instant endTime;

    public TimeSlice(Instant startTime, Instant endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeSlice of(
            int year, int month, int day,
            int hour, int minute,
            int durationInMinutes) {
        Instant start = LocalDateTime
                .of(year, month, day, hour, minute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Duration duration = Duration.ofMinutes(durationInMinutes);
        return new TimeSlice(start, start.plus(duration));
    }

    public static TimeSlice of(
            int startYear, int startMonth, int startDay,
            int startHour, int startMinute,
            int endYear, int endMonth, int endDay,
            int endHour, int endMinute) {
        Instant start = LocalDateTime
                .of(startYear, startMonth, startDay,
                        startHour, startMinute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant end = LocalDateTime
                .of(endYear, endMonth, endDay,
                        endHour, endMinute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Duration duration = Duration.between(start, end);

        return new TimeSlice(start, end);
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

    public int getStartYear() {
        return startTime.get(ChronoField.YEAR);
    }

    public int getStartMonth() {
        return startTime.get(ChronoField.MONTH_OF_YEAR);
    }

    public int getStartDay() {
        return startTime.get(ChronoField.DAY_OF_MONTH);
    }

    public int getStartHour() {
        return startTime.get(ChronoField.HOUR_OF_DAY);
    }

    public int getStartMinute() {
        return endTime.get(ChronoField.MINUTE_OF_HOUR);
    }

    public int getEndYear() {
        return endTime.get(ChronoField.YEAR);
    }

    public int getEndMonth() {
        return endTime.get(ChronoField.MONTH_OF_YEAR);
    }

    public int getEndDay() {
        return endTime.get(ChronoField.DAY_OF_MONTH);
    }

    public int getEndHour() {
        return endTime.get(ChronoField.HOUR_OF_DAY);
    }

    public int getEndMinute() {
        return endTime.get(ChronoField.MINUTE_OF_HOUR);
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public boolean conflictsWith(TimeSlice that) {
        return !(this.startTime.isAfter(that.endTime) || this.endTime.isBefore(that.startTime));
    }

    public boolean canContain(TimeSlice that) {
        return this.startTime.isBefore(that.startTime) && this.endTime.isAfter(that.endTime);
    }

    public TimeSlice mergeWith(TimeSlice that) {
        if (this.startTime.isAfter(that.startTime)) {
            this.startTime = that.startTime;
        }
        if (this.endTime.isBefore(that.endTime)) {
            this.endTime = that.endTime;
        }
        return this;
    }
}
