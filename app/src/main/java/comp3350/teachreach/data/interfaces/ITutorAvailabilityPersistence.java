package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public interface ITutorAvailabilityPersistence {
    List<ITimeSlice> getAvailability(ITutor tutor);

    void addAvailability(ITutor tutor, ITimeSlice timeRange);

    void removeAvailability(ITutor tutor, ITimeSlice timeRange);
}
