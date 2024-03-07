package comp3350.teachreach.data.interfaces;

import java.sql.Timestamp;
import java.util.List;

import comp3350.teachreach.objects.TimeSlice;

public interface ITutorAvailabilityPersistence {
    List<TimeSlice> getTutorTimeSliceByTutorID(int tutor_id);
    boolean storeTutorTimeSlice(int tutor_id, Timestamp start_time, Timestamp end_Time);
}
