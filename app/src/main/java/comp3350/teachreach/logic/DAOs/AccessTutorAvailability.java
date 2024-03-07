package comp3350.teachreach.logic.DAOs;

import java.sql.Timestamp;
import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutorAvailability;
import comp3350.teachreach.objects.TimeSlice;

public class AccessTutorAvailability {
    private static ITutorAvailability TutorAvailabilityPersistence;
    public
    AccessTutorAvailability()
    {
        TutorAvailabilityPersistence = Server.getTutorAvailabilityAccess();
    }
    AccessTutorAvailability(ITutorAvailability tutorAvailabilityAccess)
    {
        AccessTutorAvailability.TutorAvailabilityPersistence = tutorAvailabilityAccess;
    }

    public List<TimeSlice> getTutorTimeSliceByTutorID(int tutorID){
        try{
            return TutorAvailabilityPersistence.getTutorTimeSliceByTutorID(tutorID);
        }
        catch (final Exception e) {
            throw new DataAccessException(
                    "Failed to get time slice by tutor's " + "id", e);
        }
    }

   // List<TimeSlice> getTutorTimeSliceByTutorID(int tutor_id);
    public boolean storeTutorTimeSlice(int tutorID, Timestamp startTime, Timestamp endTime){
        try{

            return TutorAvailabilityPersistence.storeTutorTimeSlice(tutorID,startTime,endTime);

    } catch (final Exception e) {
        throw new DataAccessException("Failed to store tutor time slice!", e);
    }
    }
    //boolean storeTutorTimeSlice(int tutor_id, Timestamp start_time, Timestamp end_Time);
}
