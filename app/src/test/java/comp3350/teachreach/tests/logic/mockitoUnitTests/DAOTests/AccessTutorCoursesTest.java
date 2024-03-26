package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.hsqldb.PersistenceException;
import comp3350.teachreach.data.interfaces.ITutoredCoursesPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutoredCourses;
import comp3350.teachreach.logic.exceptions.DataAccessException;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ICourse;

public class AccessTutorCoursesTest {

    @Mock
    private ITutoredCoursesPersistence tutoredCoursesPersistence;

    @InjectMocks
    private AccessTutoredCourses accessTutoredCourses;

    @Test
    public void getTutoredCoursesByTutorIDTest() {
        List<ICourse> returns = new ArrayList<>();

        returns.add(new Course("COMP1012", "Introduction to Computer Science for Engineers"));
        returns.add(new Course("COMP 2080", "Analysis of Algorithms"));

        when(tutoredCoursesPersistence.getTutorCourseByTutorID(1)).thenReturn(returns);
        when(tutoredCoursesPersistence.getTutorCourseByTutorID(2)).thenThrow(new PersistenceException("Error in test, intentional"));

        List<ICourse> results = accessTutoredCourses.getTutoredCoursesByTutorID(1);

        assertEquals("Error in results from getTutoredCoursesByTutorID", results.size(), 2);
        assertEquals("Error in results from getTutoredCoursesByTutorID", results.get(0).getCourseCode(), "COMP 1012");

        assertThrows("Expected DataAccessException expected but was not thrown", DataAccessException.class, () -> accessTutoredCourses.getTutoredCoursesByTutorID(2));

    }
}
