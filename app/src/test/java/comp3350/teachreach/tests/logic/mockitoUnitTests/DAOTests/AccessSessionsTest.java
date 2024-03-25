package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.logic.DAOs.AccessSessions;
import comp3350.teachreach.objects.interfaces.ISession;

public class AccessSessionsTest {

    @Mock
    ISessionPersistence sessionPersistence;

    @InjectMocks
    AccessSessions accessSessions;

    @Test
    public void getSessionsTest() { //NEED HELP WITH THIS
        List<ISession> returns = new ArrayList<ISession>();
        returns.add()
    }
}
