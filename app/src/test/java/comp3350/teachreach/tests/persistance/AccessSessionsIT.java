package comp3350.teachreach.tests.persistance;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.teachreach.data.hsqldb.SessionHSQLDB;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.logic.DAOs.AccessSessions;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.tests.utils.TestUtils;

public
class AccessSessionsIT
{
    private AccessSessions accessSessions;
    private File           tempDB;

    @Before
    public
    void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final ISessionPersistence persistence = new SessionHSQLDB(this.tempDB
                                                                          .getAbsolutePath()
                                                                          .replace(
                                                                                  ".script",
                                                                                  ""));
        this.accessSessions = new AccessSessions(persistence);
    }
    @Test
    public void testDeleteSession(){

    }
    @Test
    public void testStoreSession(){

    }
    @Test
    public void testUpdateSession(){

    }
    @Test
    public void testGetSessionByTutorID(){}
    @Test
    public void testGetPendingSessionsByTutorID(){}

    @Test
    public void testGetPendingSessionsByStudentID(){}
    @Test
    public void testGetSessionsByStudentID(){}


//    public
//    boolean deleteSession(ISession session)

    // ISession storeSession(int studentID,
    //                          int tutorID,
    //                          TimeSlice sessionTime,
    //                          String location)

    // ISession updateSession(ISession session)
    //  List<ISession> getSessionsByTutorID(int tutorID)
    //List<ISession> getPendingSessionsByTutorID(int tutorID)
    // List<ISession> getPendingSessionsByStudentID(int studentID)
    //    List<ISession> getSessionsByStudentID(int studentID)
}
