package comp3350.teachreach.presentation.utils;

import org.threeten.bp.format.DateTimeFormatter;

import comp3350.teachreach.objects.interfaces.ITimeSlice;

public class TimeSliceFormatter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static String format(ITimeSlice timeSlot) {
        return timeSlot.getStartTime().format(formatter) + " - " + timeSlot.getEndTime().format(formatter);
    }
}
