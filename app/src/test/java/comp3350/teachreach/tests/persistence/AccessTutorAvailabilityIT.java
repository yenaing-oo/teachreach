//package comp3350.teachreach.tests.persistence;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import comp3350.teachreach.data.hsqldb.TutorAvailabilityHSQLDB;
//import comp3350.teachreach.data.interfaces.ITutorAvailabilityPersistence;
//import comp3350.teachreach.logic.DAOs.AccessTutorAvailability;
//import comp3350.teachreach.objects.TimeSlice;
//import comp3350.teachreach.objects.interfaces.ITimeSlice;
//import comp3350.teachreach.tests.utils.TestUtils;
//
//public class AccessTutorAvailabilityIT {
//    private AccessTutorAvailability accessTutorAvailability;
//    private File tempDB;
//
//    @Before
//    public
//    void setUp() throws IOException
//    {
//        this.tempDB = TestUtils.copyDB();
//        final ITutorAvailabilityPersistence persistence = new TutorAvailabilityHSQLDB(this.tempDB
//                .getAbsolutePath()
//                .replace(
//                        ".script",
//                        ""));
//        this.accessTutorAvailability = new AccessTutorAvailability(persistence);
//    }
//
//    @Test
//    public void storeTutorTimeSlice(int tutorID, ITimeSlice timeSlice) {
//        accessTutorAvailability.addAvailability(3, timeSlice);
//    }
//
//    @Test
//    public List<TimeSlice> getTutorTimeSliceByTutorID(int tutorID) {
//        return accessTutorAvailability.getAvailability(3);
//    }
//
//}
