package comp3350.teachreach.presentation.utils;

import org.threeten.bp.format.DateTimeFormatter;

import comp3350.teachreach.objects.interfaces.ITimeSlice;

public
class TimeSliceFormatter
{
    private static final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("h:mm a");
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("eee MMM d");

    public static
    String format(ITimeSlice timeSlot)
    {
        return timeSlot.getStartTime().format(dateFormat) + " at " + timeSlot.getStartTime().format(formatter) + " - " +
               timeSlot.getEndTime().format(formatter);
    }
}
