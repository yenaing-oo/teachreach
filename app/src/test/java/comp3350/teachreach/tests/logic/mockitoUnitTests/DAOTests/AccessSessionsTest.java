package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.logic.DAOs.AccessSessions;

public class AccessSessionsTest {

    @Mock
    ISessionPersistence sessionPersistence;

    @InjectMocks
    AccessSessions accessSessions;


}
