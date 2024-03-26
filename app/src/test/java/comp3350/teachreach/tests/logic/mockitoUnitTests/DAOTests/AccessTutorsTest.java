package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.exceptions.DataAccessException;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ITutor;

public class AccessTutorsTest {

    @Mock
    ITutorPersistence tutorPersistence;

    @InjectMocks
    AccessTutors accessTutors;

    @Test
    public void getTutorsTest() {
        Map<Integer, ITutor> returns = new HashMap<>();

        returns.put(1, new Tutor(1, 6));
        returns.put(2, new Tutor(2, 7));
        returns.put(3, new Tutor(3, 8));
        returns.put(4, new Tutor(4, 9));
        returns.put(5, new Tutor(5, 10));

        when(tutorPersistence.getTutors()).thenReturn(returns);

        Map<Integer, ITutor> results = accessTutors.getTutors();

        try {
            assertEquals("Incorrect result from getTutors", results.size(), 5);
            assertEquals("Incorrect result from getTutors", results.get(1).getAccountID(), 6);
            assertEquals("Incorrect result from getTutors", results.get(2).getAccountID(), 7);
            assertEquals("Incorrect result from getTutors", results.get(3).getAccountID(), 8);
            assertEquals("Incorrect result from getTutors", results.get(4).getAccountID(), 9);
            assertEquals("Incorrect result from getTutors", results.get(5).getAccountID(), 10);
        } catch(NullPointerException n) {
            fail("Issue with getTutors results");
        }
        assertThrows("Result from getTutors is not an unmodifiable map", UnsupportedOperationException.class,
                () -> results.put(6, new Tutor(6, 11)));

    }

    @Test
    public void getTutorByAccountIDTest() {
        Map<Integer, ITutor> returns = new HashMap<>();

        returns.put(1, new Tutor(1, 6));
        returns.put(2, new Tutor(2, 7));
        returns.put(3, new Tutor(3, 8));
        returns.put(4, new Tutor(4, 9));
        returns.put(5, new Tutor(5, 10));

        when(tutorPersistence.getTutors()).thenReturn(returns);

        ITutor result = accessTutors.getTutorByAccountID(8);

        assertEquals("Incorrect result from getTutorByAccountID", result.getTutorID(), 3);

        assertThrows("DataAccessException expected, but not thrown", DataAccessException.class, () -> accessTutors.getTutorByAccountID(11));
    }

    @Test
    public void getTutorByTutorIDTest() {
        Map<Integer, ITutor> returns = new HashMap<>();

        returns.put(1, new Tutor(1, 6));
        returns.put(2, new Tutor(2, 7));
        returns.put(3, new Tutor(3, 8));
        returns.put(4, new Tutor(4, 9));
        returns.put(5, new Tutor(5, 10));

        when(tutorPersistence.getTutors()).thenReturn(returns);

        ITutor result = accessTutors.getTutorByTutorID(3);

        assertEquals("Incorrect result from getTutorByAccountID", result.getAccountID(), 8);

        assertNull("DataAccessException expected, but not thrown", accessTutors.getTutorByTutorID(6));
    }

}

