package comp3350.teachreach.tests.persistence;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.hsqldb.TutoredCoursesHSQLDB;
import comp3350.teachreach.data.interfaces.ITutoredCoursesPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutoredCourses;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessTutoredCoursesIT {
    private AccessTutoredCourses accessTutoredCourses;
    private File tempDB;

    @Before
    public
    void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final ITutoredCoursesPersistence persistence = new TutoredCoursesHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        this.accessTutoredCourses = new AccessTutoredCourses(persistence);
    }
    @Test
    public void testGetTutorCourseByTID(int tutorID)
    {//   List<ICourse> getTutorCourseByTID(int tutorID);
        //accessTutoredCourses.getTutorCourseByTID(3);
        }

    @Test
    public void testStoreTutorCourse(int tutorID, int sessionID){
        //boolean storeTutorCourse(int tutorID, int sessionID);
        //accessTutoredCourses.storeTutorCourse(3,3);
    }
}
