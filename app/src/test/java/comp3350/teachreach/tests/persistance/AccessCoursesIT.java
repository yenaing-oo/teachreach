package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.teachreach.data.hsqldb.CourseHSQLDB;
import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.tests.utils.TestUtils;

public
class AccessCoursesIT
{
    private AccessCourses accessCourses;
    private File          tempDB;

    @Before
    public
    void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final ICoursePersistence persistence = new CourseHSQLDB(this.tempDB
                                                                        .getAbsolutePath()
                                                                        .replace(
                                                                                ".script",
                                                                                ""));
        this.accessCourses = new AccessCourses(persistence);
    }

    @Test
    public
    void testGetCourses()
    {
        final List<ICourse> courses = accessCourses.getCourses();
        assertEquals(1, courses.size());
    }

    @Test
    public
    void testGetCourseByCode()
    {
        final ICourse course = accessCourses.getCourseByCode("COMP 1010");
        assertNotNull(course);
        assertEquals(course.getCourseCode(), "COMP 1010");
    }

    @Test
    public
    void testGetCourseByName()
    {
        final List<ICourse> courses = accessCourses.getCoursesByName(
                "Computer Science");
        assertNotNull(courses);
        assertEquals(1, courses.size());
    }

    @Test
    public
    void testInsertCourse()
    {
        final ICourse course = new Course("COMP5555", "GOD OF COMPUTER");
        accessCourses.insertCourse(course);
        assertEquals(accessCourses.getCourses().size(), 6);
    }


}
