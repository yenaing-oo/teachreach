package comp3350.teachreach.tests.persistance;

import org.junit.Before;

import java.io.IOException;

import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.tests.utils.TestUtils;

public
class AccessTutorIT
{
    private AccessStudents accessTutors;
    @Before
    public
    void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final ITutorPersistence persistence = new TutorHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        this.accessTutors = new AccessTutors(persistence);
    }
}
