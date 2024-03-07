package comp3350.teachreach.tests.persistance;

import org.junit.Before;

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
}
