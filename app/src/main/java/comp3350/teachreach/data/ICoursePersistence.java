package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.Course;

public interface ICoursePersistence {
    ArrayList<Course> getCourses();

    void addCourse(String courseCode, String courseName);

    Course getCourseByCourseCode(String courseCode);
}
