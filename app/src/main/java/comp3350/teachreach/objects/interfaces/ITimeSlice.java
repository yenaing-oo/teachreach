package comp3350.teachreach.objects.interfaces;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

import comp3350.teachreach.objects.TimeSlice;

public interface ITimeSlice {
    LocalDateTime getStartTime();

    int getStartYear();

    int getStartMonth();

    int getStartDay();

    int getStartHour();

    int getStartMinute();

    LocalDateTime getEndTime();

    int getEndYear();

    int getEndMonth();

    int getEndDay();

    int getEndHour();

    int getEndMinute();

    Duration getDuration();

    boolean conflictsWith(ITimeSlice that);

    boolean canContain(ITimeSlice that);

    TimeSlice mergeWith(ITimeSlice that);

    boolean equals(Object obj);
}




