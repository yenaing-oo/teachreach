package comp3350.teachreach.tests.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.tests.utils.TestUtils;

public
class AccessTutorIT {
    private AccessTutors accessTutors;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final ITutorPersistence persistence = new TutorHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        this.accessTutors = new AccessTutors(persistence);
    }

    @Test
    public void testGetTutorByAccountID() {
        ITutor tutor = accessTutors.getTutorByAccountID(2);
        assertEquals(2, tutor.getAccountID());
    }


    @Test
    public void testGetTutorByTutorID() {
        ITutor tutor = accessTutors.getTutorByTutorID(1);
        assertEquals(1, tutor.getTutorID());
    }

    @Test
    public void testInsertTutor() {
        ITutor tutor = new Tutor(2);
        tutor = accessTutors.insertTutor(tutor);
        assertNotNull(tutor);
    }

    @Test
    public void testUpdateTutor() {
        ITutor tutor = accessTutors.getTutorByTutorID(1);
        tutor.setHourlyRate(20);
        ITutor testTutor = accessTutors.updateTutor(tutor);
        assertEquals(20, testTutor.getHourlyRate(), 0);
    }


    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
