package comp3350.teachreach.tests.persistance;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import comp3350.teachreach.data.hsqldb.TutorAvailabilityHSQLDB;
import comp3350.teachreach.data.interfaces.ITutorAvailabilityPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutorAvailability;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessTutorAvailabilityIT {
    private AccessTutorAvailability accessTutorAvailability;
    private File tempDB;

    @Before
    public
    void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final ITutorAvailabilityPersistence persistence = new TutorAvailabilityHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        this.accessTutorAvailability = new AccessTutorAvailability(persistence);
    }

    @Test
    public void storeTutorTimeSlice(int tutorID, Timestamp start_time, Timestamp end_Time){
        //make timestamp from timeslice
       // accessTutorAvailability.storeTutorTimeSlice(3,3,3);
    }
    @Test
    public void  getTutorTimeSliceByTutorID(int tutorID){  //need to add some on database
       // List<TimeSlice>;
        // accessTutorAvailability.getTutorTimeSliceByTutorID(3);
    }

}
