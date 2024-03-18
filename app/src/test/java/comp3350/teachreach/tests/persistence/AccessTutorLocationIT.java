package comp3350.teachreach.tests.persistence;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.hsqldb.TutorLocationHSQLDB;
import comp3350.teachreach.data.interfaces.ITutorLocationPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutorLocation;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessTutorLocationIT {
    private AccessTutorLocation accessTutorLocation;
    private File tempDB;

    @Before
    public
    void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final ITutorLocationPersistence persistence = new TutorLocationHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        this.accessTutorLocation = new AccessTutorLocation(persistence);
    }

    @Test
    public void testGetTutorLocationByTutorID(int tutorID){
        //accessTutorLocation.getTutorLocationByTutorID(3);
    } //List<String> getTutorLocationByTutorID(int tutorID);

    @Test
    public void testStoreTutorLocation(int tutorID, String location){
        //accessTutorLocation.storeTutorLocation(3,"hihi");
    }
    //boolean storeTutorLocation(int tutorID, String location);
}
