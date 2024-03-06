package comp3350.teachreach.logic.DAOs;

import java.util.List;
import java.util.NoSuchElementException;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.objects.interfaces.ICourse;

public
class AccessCourses
{
    private static ICoursePersistence coursePersistence;
    private static List<ICourse>      courses = null;

    public
    AccessCourses()
    {
        AccessCourses.coursePersistence = Server.getCourseDataAccess();
        AccessCourses.courses           = coursePersistence.getCourses();
    }

    public
    AccessCourses(ICoursePersistence courseDataAccess)
    {
        AccessCourses.coursePersistence = courseDataAccess;
        AccessCourses.courses           = coursePersistence.getCourses();
    }

    public
    List<ICourse> getCourses()
    {
        if (AccessCourses.courses == null) {
            AccessCourses.courses = coursePersistence.getCourses();
        }
        return courses;
    }

    public
    ICourse getCourseByCode(String courseCode) throws DataAccessException
    {
        return coursePersistence
                .getCourseByCourseCode(courseCode)
                .orElseThrow(() -> new DataAccessException("Course not found",
                                                           new NoSuchElementException()));
    }

    public
    List<ICourse> getCoursesByName(String courseName)
    {
        return coursePersistence.getCoursesByName(courseName);
    }

    public
    ICourse insertCourse(ICourse newCourse) throws DataAccessException
    {
        return coursePersistence
                .addCourse(newCourse.getCourseCode(), newCourse.getCourseName())
                .orElseThrow(() -> new DataAccessException(
                        "Course already exist"));
    }
}
