package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.hsqldb.PersistenceException;
import comp3350.teachreach.data.interfaces.ITutorLocationPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutorLocation;
import comp3350.teachreach.logic.DAOs.DataAccessException;

public class AccessTutorLocationTest {

    @Mock
    ITutorLocationPersistence tutorLocationPersistence;

    @InjectMocks
    AccessTutorLocation accessTutorLocation;

    @Test
    public void getTutorLocationByTutorIDTest() {
        List<String> returns = new ArrayList<String>();

        returns.add("Library");
        returns.add("Coffee Shop");
        returns.add("House");

        when(tutorLocationPersistence.getTutorLocationByTutorID(1)).thenReturn(returns);
        when(tutorLocationPersistence.getTutorLocationByTutorID(2)).thenThrow(PersistenceException.class);

        List<String> results = accessTutorLocation.getTutorLocationByTutorID(1);

        assertEquals("Error in results from getTutorLocationByTutorID", results.size(), 3);
        assertEquals("Error in results from getTutorLocationByTutorID", results.get(1), "Coffee Shop");

        assertThrows("Expected DataAccessException not thrown", DataAccessException.class, () -> accessTutorLocation.getTutorLocationByTutorID(2));

     }
}
