package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.teachreach.data.hsqldb.CourseHSQLDB;
import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.logic.DAOs.AccessCourse;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessCourseIT {
    private AccessCourse accessCourse;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final ICoursePersistence persistence =
                new CourseHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessCourse = new AccessCourse(persistence);
    }
    @Test
    public void testGetCourses() {
           final List<ICourse> courses = accessCourse.getCourses();
           assertEquals(1, courses.size());
    }

    @Test
    public void testGetCourseByCode(){
        final ICourse course = accessCourse.getCourseByCode("COMP 1010");
        assertNotNull(course);
        assertEquals(course.getCourseCode(), "COMP 1010");
    }

    @Test
    public void testGetCourseByName(){
        final List<ICourse> course = accessCourse.getCourseByName("Introduction to Computer Science");
        assertNotNull(course);
        assertEquals(course.get(0).getCourseName(),"Introduction to Computer Science");
    }

    @Test
    public void testInsertCourse(){
        final ICourse course = new Course("COMP5555", "GOD OF COMPUTER");
        accessCourse.insertCourse(course);
        assertEquals(accessCourse.getCourses().size(), 6);
    }



}
