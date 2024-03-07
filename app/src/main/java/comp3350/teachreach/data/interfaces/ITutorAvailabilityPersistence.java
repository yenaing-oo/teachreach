package comp3350.teachreach.data.interfaces;

import java.sql.Timestamp;
import java.util.List;

import comp3350.teachreach.objects.TimeSlice;

public interface ITutorAvailabilityPersistence {
    List<TimeSlice> getTutorTimeSliceByTutorID(int tutorID);
    boolean storeTutorTimeSlice(int tutorID, Timestamp start_time, Timestamp end_Time);
}
