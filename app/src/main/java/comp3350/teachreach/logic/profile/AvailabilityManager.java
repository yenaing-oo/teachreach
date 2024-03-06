package comp3350.teachreach.logic.profile;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class AvailabilityManager
{
    private final ITutor                ofTutor;
    private final List<TimeSlice>       occupiedSlices;
    private final List<List<TimeSlice>> dayOfWeekAvailability;

    public
    AvailabilityManager(ITutor tutor) throws RuntimeException
    {
        this.ofTutor          = tutor;
        dayOfWeekAvailability = ofTutor.getPreferredAvailability();
        occupiedSlices        = ofTutor
                .getFutureSessions()
                .stream()
                .map(session -> session.getTime())
                .collect(Collectors.toList());
    }

    protected
    AvailabilityManager addWeeklyAvailability(int startYear,
                                              int startMonth,
                                              int startDay,
                                              int startHour,
                                              int startMinute,
                                              int endYear,
                                              int endMonth,
                                              int endDay,
                                              int endHour,
                                              int endMinute,
                                              List<DayOfWeek> daysOfWeek)
    {

        final LocalDate startDate = LocalDate.of(startYear,
                                                 startMonth,
                                                 startDay);
        final LocalDate endDate   = LocalDate.of(endYear, endMonth, endDay);
        final LocalTime startTime = LocalTime.of(startHour, startMinute);
        final LocalTime endTime   = LocalTime.of(endHour, endMinute);
        final ZoneId    zoneId    = ZoneId.systemDefault();

        LocalDate currDate = startDate;
        while (!currDate.isAfter(endDate)) {
            final DayOfWeek currDayOfWeek = currDate.getDayOfWeek();
            if (daysOfWeek.contains(currDayOfWeek)) {
                ZonedDateTime startDateTime = ZonedDateTime.of(currDate,
                                                               startTime,
                                                               zoneId);
                ZonedDateTime endDateTime = ZonedDateTime.of(currDate,
                                                             endTime,
                                                             zoneId);
                TimeSlice timeSlice = new TimeSlice(startDateTime.toInstant(),
                                                    endDateTime.toInstant(),
                                                    Duration.between(
                                                            startDateTime,
                                                            endDateTime));
                List<TimeSlice> toBeAdded = new ArrayList<>();
                for (TimeSlice that : dayOfWeekAvailability.get(
                        currDayOfWeek.getValue() - 1)) {
                    if (that.conflictsWith(timeSlice)) {
                        that.mergeWith(timeSlice);
                    } else {
                        toBeAdded.add(that);
                    }
                }
                toBeAdded.forEach(dayOfWeekAvailability.get(
                        currDayOfWeek.getValue() - 1)::add);
            }
            currDate = currDate.plusDays(1);
        }
        ofTutor.setPreferredAvailability(dayOfWeekAvailability);
        return this;
    }

    protected
    AvailabilityManager clearWeeklyAvailability()
    {
        this.dayOfWeekAvailability.forEach(List::clear);
        return this;
    }

    public
    List<List<TimeSlice>> getWeeklyAvailability()
    {
        return this.dayOfWeekAvailability;
    }

    public
    boolean isAvailableAt(final TimeSlice timeSlice)
    {
        boolean preferenceMet = this.dayOfWeekAvailability
                .stream()
                .flatMap(List::stream)
                .anyMatch(preferredSlice -> preferredSlice.canContain(timeSlice));
        boolean notOccupied = this.occupiedSlices
                .stream()
                .noneMatch(t -> t.conflictsWith(timeSlice));
        return notOccupied && preferenceMet;
    }

    public
    List<TimeSlice> getAvailableTimeSlotOfRange(int startYear,
                                                int startMonth,
                                                int startDay,
                                                int startHour,
                                                int startMinute,
                                                int endYear,
                                                int endMonth,
                                                int endDay,
                                                int endHour,
                                                int endMinute)
    {
        final TimeSlice range = TimeSlice.of(startYear,
                                             startMonth,
                                             startDay,
                                             startHour,
                                             startMinute,
                                             endYear,
                                             endMonth,
                                             endDay,
                                             endHour,
                                             endMinute);
        return this.dayOfWeekAvailability
                .stream()
                .flatMap(List::stream)
                .filter(timeSlice -> range.canContain(timeSlice) &&
                                     isAvailableAt(timeSlice))
                .collect(Collectors.toList());
    }
}
