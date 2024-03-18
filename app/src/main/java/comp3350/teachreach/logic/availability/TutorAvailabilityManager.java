package comp3350.teachreach.logic.availability;

import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutorAvailabilityPersistence;
import comp3350.teachreach.logic.exceptions.availability.TutorAvailabilityManagerException;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorAvailabilityManager implements ITutorAvailabilityManager {

    private final ITutorAvailabilityPersistence accessTutorAvailability;

    public TutorAvailabilityManager() {
        this.accessTutorAvailability = Server.getTutorAvailabilityAccess();
    }

    public TutorAvailabilityManager(ITutorAvailabilityPersistence tutorAvailabilityPersistence) {
        this.accessTutorAvailability = tutorAvailabilityPersistence;
    }


    @Override
    public List<ITimeSlice> getAvailabilty(ITutor tutor) {
        return accessTutorAvailability.getAvailability(tutor);
    }

    public boolean isAvailableAt(ITutor tutor, ITimeSlice timeRange) {
        List<ITimeSlice> tutorAvailability = getAvailabilty(tutor);
        return doesNotConflict(timeRange, tutorAvailability);
    }

    @Override
    public void addAvailability(ITutor tutor, ITimeSlice timeRange) throws TutorAvailabilityManagerException {
        List<ITimeSlice> tutorAvailability = getAvailabilty(tutor);
        if (doesNotConflict(timeRange, tutorAvailability)) {
            accessTutorAvailability.addAvailability(tutor, timeRange);
        } else {
            throw new TutorAvailabilityManagerException("Cannot add availability that overlaps with existing availability");
        }
    }

    @Override
    public void removeAvailability(ITutor tutor, ITimeSlice timeRange) {
        List<TimeSlice> tutorAvailability = getAvailabilty(tutor);
        if (tutorAvailability.contains(timeRange)) {
            accessTutorAvailability.removeAvailability(tutor, timeRange);
        }
    }

    private boolean doesNotConflict(ITimeSlice timeSlice, List<ITimeSlice> timeSlices) {
        return timeSlices
                .stream()
                .noneMatch(t -> t.conflictsWith(timeSlice));
    }

}
