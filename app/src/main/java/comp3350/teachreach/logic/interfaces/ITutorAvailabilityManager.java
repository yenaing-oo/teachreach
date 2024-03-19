package comp3350.teachreach.logic.interfaces;

import java.util.List;

import comp3350.teachreach.logic.exceptions.availability.TutorAvailabilityManagerException;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public interface ITutorAvailabilityManager {
    List<ITimeSlice> getAvailabilty(ITutor tutor);

    boolean isAvailableAt(ITutor tutor, ITimeSlice timeRange);

    void addAvailability(ITutor tutor, ITimeSlice timeRange) throws TutorAvailabilityManagerException;

    void removeAvailability(ITutor tutor, ITimeSlice timeRange);
}