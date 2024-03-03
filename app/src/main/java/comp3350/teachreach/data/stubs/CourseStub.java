package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.Iterator;

import comp3350.teachreach.objects.Course;

public class CourseStub implements comp3350.teachreach.data.ICoursePersistence {

    ArrayList<Course> courses;

    public CourseStub() {

        courses = new ArrayList<>();
        this.courses.add(new Course("COMP 2080", "Analysis of Algorithms"));
        this.courses.add(new Course("COMP 1010", "Introduction to Computer Science"));
        this.courses.add(new Course("COMP 1012", "Introduction to Computer Science for Engineers"));
        this.courses.add(new Course("COMP 2150", "Object Orientation"));
        this.courses.add(new Course("COMP 3380", "Databases Concepts and Usage"));
    }
    //add your function


    @Override
    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    @Override
    public void addCourse(String courseCode, String courseName) {
        this.courses.add(new Course(courseCode, courseName));
    }

    @Override
    public Course getCourseByCourseCode(String courseCode) {
        Course course = null;
        boolean found = false;
        Iterator<Course> courseIterator = courses.iterator();
        while (courseIterator.hasNext() && !found) {
            course = courseIterator.next();
            found = course.getCourseCode().equals(courseCode);
        }
        return course;
    }

    //eg. add, search, delete
}
