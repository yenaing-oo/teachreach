package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ICourse;

public class CourseStub implements ICoursePersistence {

    List<ICourse> courses;

    public CourseStub() {

        courses = new ArrayList<>();
        this.courses.add(new Course("COMP 2080", "Analysis of Algorithms"));
        this.courses.add(new Course("COMP 1010", "Introduction to Computer Science"));
        this.courses.add(new Course("COMP 1012", "Introduction to Computer Science for Engineers"));
        this.courses.add(new Course("COMP 2150", "Object Orientation"));
        this.courses.add(new Course("COMP 3380", "Databases Concepts and Usage"));
    }

    @Override
    public List<ICourse> getCourses() {
        return this.courses;
    }

    @Override
    public boolean addCourse(String courseCode, String courseName) {
        return this.courses.add(new Course(courseCode, courseName));
    }

    @Override
    public Optional<ICourse> getCourseByCourseCode(String courseCode) {
        return courses.stream()
                .filter(course -> course.getCourseCode().equals(courseCode))
                .findFirst();
    }

    @Override
    public List<ICourse> getCoursesByName(String courseName) {
        return courses.stream()
                .filter(course -> course.getCourseName().contains(courseName))
                .collect(Collectors.toList());
    }
}
