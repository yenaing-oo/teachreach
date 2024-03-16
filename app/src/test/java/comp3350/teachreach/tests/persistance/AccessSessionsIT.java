package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.exceptions.DuplicateEmailException;
import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.data.hsqldb.SessionHSQLDB;
import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessSessions;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.exceptions.AccountCreatorException;
import comp3350.teachreach.logic.exceptions.InvalidNameException;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;
import comp3350.teachreach.logic.interfaces.IAccountCreator;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessSessionsIT
{
    private AccessSessions  accessSessions;
    private AccessAccounts  accessAccounts;
    private AccessStudents  accessStudents;
    private AccessTutors    accessTutors;
    private IStudent        testStudent;
    private ITutor          testTutor;
    private File            tempDB;

    @Before
    public void setUp() throws
                        IOException,
                        InvalidNameException,
                        DuplicateEmailException,
                        InvalidPasswordException,
                        InvalidEmailException,
                        AccountCreatorException
    {
        this.tempDB = TestUtils.copyDB();
        final IAccountPersistence accountPersistence
                = new AccountHSQLDB(this.tempDB
                                            .getAbsolutePath()
                                            .replace(".script", ""));
        final ISessionPersistence sessionPersistence
                = new SessionHSQLDB(this.tempDB
                                            .getAbsolutePath()
                                            .replace(".script", ""));
        final IStudentPersistence studentPersistence
                = new StudentHSQLDB(this.tempDB
                                            .getAbsolutePath()
                                            .replace(".script", ""));
        final ITutorPersistence tutorPersistence = new TutorHSQLDB(this.tempDB
                                                                           .getAbsolutePath()
                                                                           .replace(
                                                                                   ".script",
                                                                                   ""));
        this.accessAccounts = new AccessAccounts(accountPersistence);
        this.accessSessions = new AccessSessions(sessionPersistence);
        this.accessTutors   = new AccessTutors(tutorPersistence);
        this.accessStudents = new AccessStudents(studentPersistence);
        IAccountCreator accountCreator = new AccountCreator(accountPersistence,
                                                            studentPersistence,
                                                            tutorPersistence);
        testStudent         = accountCreator.createStudentAccount("asit@s.s",
                                                                  "pwd",
                                                                  "aStudent",
                                                                  "it",
                                                                  "CS");
        testTutor           = accountCreator.createTutorAccount("asit@t.t",
                                                                "pwd",
                                                                "aTutor",
                                                                "it",
                                                                "CS");
    }

    @Test
    public void testStoreAccessSession()
    {
        TimeSlice sessionTime = TimeSlice.ofHalfAnHourFrom(2023,
                                                           12,
                                                           21,
                                                           10,
                                                           30);
        String sessionLocation = "420 Feltcher Argue";
        ISession storedSession
                = accessSessions.storeSession(testStudent.getStudentID(),
                                              testTutor.getTutorID(),
                                              sessionTime,
                                              sessionLocation);
        ISession retrievedSession = accessSessions
                .getSessions()
                .get(storedSession.getSessionID());
        assertNotNull(retrievedSession);
        assertEquals(2023, retrievedSession.getTime().getStartYear());
        assertEquals(12, retrievedSession.getTime().getStartMonth());
        assertEquals(21, retrievedSession.getTime().getStartDay());
        assertEquals(10, retrievedSession.getTime().getStartHour());
        assertEquals(30, retrievedSession.getTime().getStartMinute());
        assertEquals(2023, retrievedSession.getTime().getEndYear());
        assertEquals(12, retrievedSession.getTime().getEndMonth());
        assertEquals(21, retrievedSession.getTime().getEndDay());
        assertEquals(11, retrievedSession.getTime().getEndHour());
        assertEquals(0, retrievedSession.getTime().getEndMinute());
        assertEquals(testStudent.getStudentID(),
                     retrievedSession.getSessionStudentID());
        assertEquals(testTutor.getTutorID(),
                     retrievedSession.getSessionTutorID());
    }

    @Test
    public void testDeleteSession()
    {
        TimeSlice sessionTime = TimeSlice.ofHalfAnHourFrom(2023,
                                                           12,
                                                           21,
                                                           10,
                                                           30);
        String sessionLocation = "420 Feltcher Argue";
        ISession storedSession
                = accessSessions.storeSession(testStudent.getStudentID(),
                                              testTutor.getTutorID(),
                                              sessionTime,
                                              sessionLocation);
        ISession retrievedSession = accessSessions
                .getSessions()
                .get(storedSession.getSessionID());
        assertNotNull(retrievedSession);
        assertTrue(accessSessions.deleteSession(retrievedSession));
        assertFalse(accessSessions.deleteSession(retrievedSession.getSessionID()));
        retrievedSession = accessSessions
                .getSessions()
                .get(storedSession.getSessionID());
        assertNull(retrievedSession);
    }

    @Test
    public void testUpdateSession()
    {
        TimeSlice sessionTime = TimeSlice.ofHalfAnHourFrom(2023,
                                                           12,
                                                           21,
                                                           10,
                                                           30);
        TimeSlice newSessionTime = TimeSlice.ofHalfAnHourFrom(2023,
                                                              12,
                                                              31,
                                                              23,
                                                              30);
        String sessionLocation    = "420 Feltcher Argue";
        String newSessionLocation = "469 Feltcher Argue";
        ISession storedSession
                = accessSessions.storeSession(testStudent.getStudentID(),
                                              testTutor.getTutorID(),
                                              sessionTime,
                                              sessionLocation);
        ISession retrievedSession = accessSessions
                .getSessions()
                .get(storedSession.getSessionID());
        assertNotNull(retrievedSession);
        retrievedSession.setSessionLocation(newSessionLocation);
        retrievedSession.approvedByTutor();
        retrievedSession.setTime(newSessionTime);
        retrievedSession = accessSessions.updateSession(retrievedSession);
        assertNotNull(retrievedSession);
        assertEquals(2023, retrievedSession.getTime().getStartYear());
        assertEquals(12, retrievedSession.getTime().getStartMonth());
        assertEquals(31, retrievedSession.getTime().getStartDay());
        assertEquals(23, retrievedSession.getTime().getStartHour());
        assertEquals(30, retrievedSession.getTime().getStartMinute());
        assertEquals(2024, retrievedSession.getTime().getEndYear());
        assertEquals(1, retrievedSession.getTime().getEndMonth());
        assertEquals(1, retrievedSession.getTime().getEndDay());
        assertEquals(0, retrievedSession.getTime().getEndHour());
        assertEquals(0, retrievedSession.getTime().getEndMinute());
        assertEquals(newSessionLocation, retrievedSession.getSessionLocation());
        assertTrue(retrievedSession.getAcceptedStatus());
    }

    @After
    public void tearDown()
    {
        this.tempDB.delete();
    }
}
