package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;


import java.util.HashMap;
import java.util.Map;

import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.objects.interfaces.ICourse;

public class AccessCoursesTest {

    @Mock
    private static ICoursePersistence coursePersistence;

    @InjectMocks
    private AccessCourses accessCourses;
    @Test
    public void getCoursesTest() {
        Map<String, ICourse> returns = new HashMap<>();
        when(coursePersistence.getCourses()).thenReturn();
    }
}
