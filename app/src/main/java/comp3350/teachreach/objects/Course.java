package comp3350.teachreach.objects;

import androidx.annotation.NonNull;

import comp3350.teachreach.objects.interfaces.ICourse;

public class Course implements ICourse {
    private String courseCode;
    private String courseName;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean equals(Course otherCourse) {
        return this.courseCode.equalsIgnoreCase(otherCourse.courseCode);
    }

    @NonNull
    @Override
    public String toString() {
        return this.courseCode + ": " + this.courseName;
    }
}
