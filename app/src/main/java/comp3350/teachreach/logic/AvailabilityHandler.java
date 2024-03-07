package comp3350.teachreach.logic;

import java.time.LocalDate;
import java.util.List;

import comp3350.teachreach.logic.interfaces.IAvailabilityHandler;
import comp3350.teachreach.objects.TimeSlice;

public class AvailabilityHandler implements IAvailabilityHandler {

    @Override
    public List<TimeSlice> getAvailiability(LocalDate date) {
        return null;
    }

    @Override
    public boolean isAvailiable(TimeSlice timeslice) {
        return false;
    }
}
