package comp3350.teachreach.logic.interfaces;

import java.time.LocalDate;
import java.util.List;

import comp3350.teachreach.objects.TimeSlice;

public interface IAvailabilityHandler {
    List<TimeSlice> getAvailiability(LocalDate date);

    boolean isAvailiable(TimeSlice timeslice);
}
