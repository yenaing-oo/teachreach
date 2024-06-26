package comp3350.teachreach.logic.availability;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.application.TRData;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ITimeSlice;

public class TimeSlotGenerator {
    public static int TIME_SLOT_LENGTH;

    static {
        TimeSlotGenerator.TIME_SLOT_LENGTH = TRData.getTimeSlotLength();
    }

    public static List<ITimeSlice> generateTimeSlots(ITimeSlice timeRange) {
        List<ITimeSlice> timeSlots = new ArrayList<>();
        LocalDateTime startTime = timeRange.getStartTime();
        LocalDateTime endTime = timeRange.getEndTime();

        LocalDateTime currentSlotStartTime = calculateNearestInterval(startTime, TIME_SLOT_LENGTH);

        while (!currentSlotStartTime.isAfter(endTime)) {
            LocalDateTime currentSlotEndTime = currentSlotStartTime.plusMinutes(TIME_SLOT_LENGTH);
            if (currentSlotEndTime.isAfter(endTime)) {
                break;
            }
            timeSlots.add(new TimeSlice(currentSlotStartTime, currentSlotEndTime));
            currentSlotStartTime = currentSlotEndTime;
        }

        return timeSlots;
    }

    private static LocalDateTime calculateNearestInterval(LocalDateTime time, int timeSlotLength) {
        int minute = time.getMinute();
        int offset = (minute % timeSlotLength == 0) ? 0 : (timeSlotLength - (minute % timeSlotLength));
        return time.plusMinutes(offset);
    }
}
