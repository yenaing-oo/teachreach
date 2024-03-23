package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.DataAccessException;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ICourse;

public class AccessCoursesTest {

    @Mock
    private static ICoursePersistence coursePersistence;

    @InjectMocks
    private AccessCourses accessCourses;
    @Test
    public void getCoursesTest() {
        Map<String, ICourse> returns = new HashMap<>();
        returns.put("COMP 2080",
                new Course("COMP 2080",
                        "Analysis of Algorithms"));
        returns.put("COMP 1010",
                new Course("COMP 1010",
                        "Introduction to Computer Science"));
        returns.put("COMP 1012",
                new Course("COMP 1012",
                        "Introduction to Computer Science " +
                                "for Engineers"));
        returns.put("COMP 2150",
                new Course("COMP 2150", "Object Orientation"));
        returns.put("COMP 3380",
                new Course("COMP 3380",
                        "Databases Concepts and Usage"));
        when(coursePersistence.getCourses()).thenReturn(returns);

        Map<String, ICourse> result = accessCourses.getCourses();

        assertEquals(result.size(), 5);
        assertEquals("Issue with getCourses return contents",result.get("COMP 2080").getCourseName(), "Analysis of Algorithms");
        assertEquals("Issue with getCourses return contents", result.get("COMP 1010").getCourseName(), "Introduction to Computer Science");
        assertEquals("Issue with getCourses return contents", result.get("COMP 1012").getCourseName(), "Introduction to Computer Science for Engineers");
        assertEquals("Issue with getCourses return contents", result.get("COMP 2150").getCourseName(), "Object Orientation");
        assertEquals("Issue with getCourses return contents", result.get("COMP 3380").getCourseName(), "Databases Concepts and Usage");

        assertThrows("Result from getAccounts is not an unmodifiable map", UnsupportedOperationException.class,
                () -> result.put("COMP 4620",
                        new Course("COMP 4620",
                                "Professional Practice in Computer Science")));
    }

    @Test
    public void getCourseByCodeTest() {
        Map<String, ICourse> returns = new HashMap<>();
        returns.put("COMP 2080",
                new Course("COMP 2080",
                        "Analysis of Algorithms"));
        returns.put("COMP 1010",
                new Course("COMP 1010",
                        "Introduction to Computer Science"));
        returns.put("COMP 1012",
                new Course("COMP 1012",
                        "Introduction to Computer Science " +
                                "for Engineers"));
        returns.put("COMP 2150",
                new Course("COMP 2150", "Object Orientation"));
        returns.put("COMP 3380",
                new Course("COMP 3380",
                        "Databases Concepts and Usage"));
        when(coursePersistence.getCourses()).thenReturn(returns);

        ICourse result = accessCourses.getCourseByCode("COMP 1012");

        assertEquals("Issue with getCourseByCode result", result.getCourseName(), "Introduction to Computer Science for Engineers");
        assertThrows("DataAccessException expected from getCourseByCode", DataAccessException.class, () -> accessCourses.getCourseByCode("HelloWorld"));
    }

    @Test
    public void getCoursesByNameTest() {
        Map<String, ICourse> returns = new HashMap<>();
        returns.put("COMP 2080",
                new Course("COMP 2080",
                        "Analysis of Algorithms"));
        returns.put("COMP 1010",
                new Course("COMP 1010",
                        "Introduction to Computer Science"));
        returns.put("COMP 1012",
                new Course("COMP 1012",
                        "Introduction to Computer Science " +
                                "for Engineers"));
        returns.put("COMP 2150",
                new Course("COMP 2150", "Object Orientation"));
        returns.put("COMP 3380",
                new Course("COMP 3380",
                        "Databases Concepts and Usage"));
        when(coursePersistence.getCourses()).thenReturn(returns);

        List<ICourse> result = accessCourses.getCoursesByName("Object Orientation");

        assertEquals("Issue with getCoursesByName result", result.get(0).getCourseName(), "COMP 2150");

        assertEquals("Unexpected result from getCoursesByName", accessCourses.getCoursesByName("HelloWorld").size(), 0);
    }
}
