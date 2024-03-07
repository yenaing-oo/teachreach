package comp3350.teachreach.tests.persistance;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.tests.utils.TestUtils;

public
class AccessTutorIT
{
    private AccessTutors accessTutors;
    private File tempDB;
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
    @Test
    public void testGetTutorByAccountID(){

    }
    @Test
    public void testgetTutorByTutorID(){}
    @Test
    public void testInsertTutor(){}
    @Test
    public void testUpdateTutor(){}
    //
    //
    //
    // ITutor getTutorByAccountID(int accountID)
    //ITutor getTutorByTutorID(int tutorID)
    // ITutor insertTutor(ITutor newTutor)
    // ITutor updateTutor(ITutor existingTutor)
}
