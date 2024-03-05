package comp3350.teachreach.logic.DAOs;

import java.util.List;
import java.util.NoSuchElementException;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.objects.interfaces.ICourse;

public class AccessCourse {
    private static ICoursePersistence coursePersistence;
    private static List<ICourse> courses = null;
    //private ICourse course = null;

    public AccessCourse() {
        AccessCourse.coursePersistence = Server.getCourseDataAccess();
        //AccessCourse.courses = coursePersistence.getCourses();
    }

    public AccessCourse(ICoursePersistence courseDataAccess) {
        AccessCourse.coursePersistence = courseDataAccess;
        //AccessCourse.courses = coursePersistence.getCourses();
    }

    public static List<ICourse> getCourses() {
        if (AccessCourse.courses == null) {
            AccessCourse.courses = coursePersistence.getCourses();
        }
        return courses;
    }

    public ICourse getCourseByCode(String courseCode) {
//        if (AccessCourse.courses == null) {
//            AccessCourse.courses = coursePersistence.getCourses();
//        }
//        return courses
//                .stream()
//                .filter(c -> c.getCourseCode().equals(courseCode))
//                .findFirst()
//                .orElseThrow(NoSuchElementException::new);
        return coursePersistence.getCourseByCourseCode(courseCode)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<ICourse> getCourseByName(String courseName) {
//        if (AccessCourse.courses == null) {
//            AccessCourse.courses = coursePersistence.getCourses();
//        }
//        return courses
//                .stream()
//                .filter(c -> c.getCourseCode().equals(courseCode))
//                .findFirst()
//                .orElseThrow(NoSuchElementException::new);
        return coursePersistence.getCoursesByName(courseName);
    }
    public ICourse insertCourse(ICourse newCourse) {
        return coursePersistence.addCourse(newCourse.getCourseCode(),
                newCourse.getCourseName()) ? newCourse : null;
    }
}
