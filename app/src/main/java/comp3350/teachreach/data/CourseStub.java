package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.Course;
public class CourseStub {

    ArrayList<Course> courses;

    public CourseStub() {
        this.courses = new ArrayList<Course>();

        this.courses.add(new Course("COMP 2080", "Analysis of Algorithms"));
        this.courses.add(new Course("COMP 1010", "Introduction to Computer Science"));
        this.courses.add(new Course("COMP 1012", "Introduction to Computer Science for Engineers"));
        this.courses.add(new Course("COMP 2150", "Object Orientation"));
        this.courses.add(new Course("COMP 3380", "Databases Concepts and Usage"));
    }
    //add your function


    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public void addCourse(String courseCode, String courseName) {
        this.courses.add(new Course(courseCode, courseName));

    }

    //eg. add, search, delete
}
