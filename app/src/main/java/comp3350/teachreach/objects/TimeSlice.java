package comp3350.teachreach.objects;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class TimeSlice
{
    private Instant  startTime;
    private Instant  endTime;
    private Duration duration;

    public TimeSlice(Instant startTime, Instant endTime)
    {
        this.startTime = startTime;
        this.endTime   = endTime;
        this.duration  = Duration.between(startTime, endTime);
    }

    public static TimeSlice ofHalfAnHourFrom(int year,
                                             int month,
                                             int dayOfMonth,
                                             int hour,
                                             int minute)
    {
        Instant start = LocalDateTime
                .of(year, month, dayOfMonth, hour, minute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return new TimeSlice(start, start.plus(Duration.ofMinutes(30)));
    }

    public static TimeSlice of(int startYear,
                               int startMonth,
                               int startDay,
                               int startHour,
                               int startMinute,
                               int endYear,
                               int endMonth,
                               int endDay,
                               int endHour,
                               int endMinute)
    {
        Instant start = LocalDateTime
                .of(startYear, startMonth, startDay, startHour, startMinute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant end = LocalDateTime
                .of(endYear, endMonth, endDay, endHour, endMinute)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return new TimeSlice(start, end);
    }

    public Instant getStartTime()
    {
        return startTime;
    }

    public OffsetDateTime getStartODT(ZoneId zone)
    {
        return OffsetDateTime.ofInstant(startTime, zone);
    }

    public OffsetDateTime getEndODT(ZoneId zone)
    {
        return OffsetDateTime.ofInstant(endTime, zone);
    }

    public int getStartYear()
    {
        return startTime.atZone(ZoneId.systemDefault()).getYear();
    }

    public int getStartMonth()
    {
        return startTime.atZone(ZoneId.systemDefault()).getMonthValue();
    }

    public int getStartDay()
    {
        return startTime.atZone(ZoneId.systemDefault()).getDayOfMonth();
    }

    public int getStartHour()
    {
        return startTime.atZone(ZoneId.systemDefault()).getHour();
    }

    public int getStartMinute()
    {
        return startTime.atZone(ZoneId.systemDefault()).getMinute();
    }

    public int getEndYear()
    {
        return endTime.atZone(ZoneId.systemDefault()).getYear();
    }

    public int getEndMonth()
    {
        return endTime.atZone(ZoneId.systemDefault()).getMonthValue();
    }

    public int getEndDay()
    {
        return endTime.atZone(ZoneId.systemDefault()).getDayOfMonth();
    }

    public int getEndHour()
    {
        return endTime.atZone(ZoneId.systemDefault()).getHour();
    }

    public int getEndMinute()
    {
        return endTime.atZone(ZoneId.systemDefault()).getMinute();
    }

    public Instant getEndTime()
    {
        return endTime;
    }

    public Duration getDuration()
    {
        return duration;
    }

    public boolean conflictsWith(TimeSlice that)
    {
        return that.endTime.isAfter(this.startTime) ||
               that.startTime.isBefore(this.endTime);
    }

    public boolean canContain(TimeSlice that)
    {
        return this.startTime.isBefore(that.startTime) &&
               this.endTime.isAfter(that.endTime);
    }

    public TimeSlice mergeWith(TimeSlice that)
    {
        if (this.startTime.isAfter(that.startTime)) {
            this.startTime = that.startTime;
        }
        if (this.endTime.isBefore(that.endTime)) {
            this.endTime = that.endTime;
        }
        this.duration = Duration.between(this.startTime, this.endTime);
        return this;
    }
}
