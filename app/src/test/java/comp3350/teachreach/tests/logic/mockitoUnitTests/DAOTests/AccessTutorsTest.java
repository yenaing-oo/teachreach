package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutors;

public class AccessTutorsTest {

    @Mock
    ITutorPersistence tutorPersistence;

    @InjectMocks
    AccessTutors accessTutors;

    @Test
    public void getTutorsTest() {

    }
}
