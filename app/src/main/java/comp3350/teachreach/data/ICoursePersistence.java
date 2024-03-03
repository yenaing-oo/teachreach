package comp3350.teachreach.data;

import java.util.List;
import java.util.Optional;

import comp3350.teachreach.objects.ICourse;

public interface ICoursePersistence {
    List<ICourse> getCourses();

    boolean addCourse(String courseCode, String courseName);

    Optional<ICourse> getCourseByCourseCode(String courseCode);

    List<ICourse> getCoursesByName(String courseName);
}
