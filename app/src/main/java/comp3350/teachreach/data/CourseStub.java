package comp3350.teachreach.data;

import comp3350.teachreach.objects.*;
import java.util.ArrayList;
import java.util.Random;
public class CourseStub {

    ArrayList<Course> courses;

        public CourseStub() {


            this.courses.add(new Course("COMP 2080", "Analysis of Algorithms"));
            this.courses.add(new Course("COMP 1010", "Introduction to Computer Science"));
            this.courses.add(new Course("COMP 1012", "Introduction to Computer Science for Engineers"));
            this.courses.add(new Course("COMP 2150", "Object Orientation"));
            this.courses.add(new Course("COMP 3380", "Databases Concepts and Usage"));
    }
    //add your function
    //eg. add, search, delete
}
