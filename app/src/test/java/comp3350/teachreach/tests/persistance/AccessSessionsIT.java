package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    private IAccountCreator accountCreator;
    private File            tempDB;

    @Before
    public void setUp() throws IOException
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
        this.accountCreator = new AccountCreator(accountPersistence,
                                                 studentPersistence,
                                                 tutorPersistence);
    }

    @Test
    public void testStoreSession() throws
                                   InvalidNameException,
                                   DuplicateEmailException,
                                   InvalidPasswordException,
                                   InvalidEmailException,
                                   AccountCreatorException
    {
        IStudent testStudent = accountCreator.createStudentAccount("asit@s.s",
                                                                   "pwd",
                                                                   "aStudent",
                                                                   "it",
                                                                   "CS");
        ITutor testTutor = accountCreator.createTutorAccount("asit@t.t",
                                                             "pwd",
                                                             "aTutor",
                                                             "it",
                                                             "CS");
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
    }

    @After
    public void tearDown()
    {
        this.tempDB.delete();
    }
}
