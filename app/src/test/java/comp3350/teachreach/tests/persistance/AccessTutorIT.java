package comp3350.teachreach.tests.persistance;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.objects.interfaces.ITutor;
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
    //
    //
    //
    @Test
    public void testGetTutorByAccountID(){
        ITutor tutor = accessTutors.getTutorByAccountID(1);
        assertEquals(1, tutor.getAccountID());
    }


    @Test
    public void testGetTutorByTutorID(){
        ITutor tutor = accessTutors.getTutorByTutorID(1);
        assertEquals(1, tutor.getTutorID());
    }
    // ITutor getTutorByAccountID(int accountID)
    //ITutor getTutorByTutorID(int tutorID)
    // ITutor insertTutor(ITutor newTutor)
    // ITutor updateTutor(ITutor existingTutor)
}
