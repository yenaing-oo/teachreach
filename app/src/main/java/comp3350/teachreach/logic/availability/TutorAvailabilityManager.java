package comp3350.teachreach.logic.availability;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.data.interfaces.ITutorAvailabilityPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.exceptions.availability.TutorAvailabilityManagerException;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorAvailabilityManager implements ITutorAvailabilityManager
{

    private final ITutorAvailabilityPersistence accessTutorAvailability;

    public TutorAvailabilityManager()
    {
        this.accessTutorAvailability = Server.getTutorAvailabilityAccess();
    }

    public TutorAvailabilityManager(ITutorAvailabilityPersistence tutorAvailabilityPersistence,
                                    ITutorPersistence tutorPersistence,
                                    ISessionPersistence sessionPersistence)
    {
        this.accessTutorAvailability = tutorAvailabilityPersistence;
    }

    @Override
    public List<ITimeSlice> getAvailability(ITutor tutor)
    {
        return accessTutorAvailability.getAvailability(tutor);
    }

    @Override
    public List<ITimeSlice> getAvailabilityAsSlots(ITutor tutor, LocalDate date)
    {
        List<ITimeSlice> availability = getAvailabilityOnDay(tutor, date);
        List<ITimeSlice> timeSlots    = new ArrayList<>();

        for (ITimeSlice timeRange : availability) {
            timeSlots.addAll(TimeSlotGenerator.generateTimeSlots(timeRange));
        }
        return timeSlots;
    }

    @Override
    public List<ITimeSlice> getAvailabilityOnDay(ITutor tutor, LocalDate date)
    {
        return accessTutorAvailability.getAvailabilityOnDay(tutor, date);
    }

    public ITimeSlice isAvailableAt(ITutor tutor, ITimeSlice timeRange)
    {
        List<ITimeSlice> tutorAvailability = getAvailability(tutor);
        return getAvailableTimeRange(timeRange, tutorAvailability);
    }

    @Override
    public void addAvailability(ITutor tutor, ITimeSlice timeRange) throws TutorAvailabilityManagerException {
        List<ITimeSlice> tutorAvailability = getAvailability(tutor);
        if (!overlapsExistingAvailability(timeRange, tutorAvailability)) {
            accessTutorAvailability.addAvailability(tutor, timeRange);
        } else {
            throw new TutorAvailabilityManagerException(
                    "Cannot add availability that overlaps with" +
                            "existing " +
                            "availability");
        }
    }

    @Override
    public void removeAvailability(ITutor tutor, ITimeSlice timeRange)
            throws TutorAvailabilityManagerException {
        List<ITimeSlice> tutorAvailability = getAvailability(tutor);
        if (tutorAvailability.contains(timeRange)) {
            accessTutorAvailability.removeAvailability(tutor, timeRange);
        } else {
            throw new TutorAvailabilityManagerException(
                    "Cannot remove availability that does not exist");
        }
    }

    private boolean overlapsExistingAvailability(ITimeSlice timeSlice,
                                                 List<ITimeSlice> timeSlices) {
        return timeSlices.stream().anyMatch(t -> t.conflictsWith(timeSlice));
    }

    private ITimeSlice getAvailableTimeRange(ITimeSlice timeSlice,
                                             List<ITimeSlice> timeSlices) {
        return timeSlices
                .stream()
                .filter(t -> t.canContain(timeSlice))
                .findFirst()
                .orElse(null);
    }
}
