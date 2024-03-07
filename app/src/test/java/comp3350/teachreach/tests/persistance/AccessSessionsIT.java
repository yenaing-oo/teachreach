package comp3350.teachreach.tests.persistance;

import org.junit.Before;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.hsqldb.SessionHSQLDB;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.logic.DAOs.AccessSessions;
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

}
