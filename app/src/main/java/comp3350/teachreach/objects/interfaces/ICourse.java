package comp3350.teachreach.objects.interfaces;

import comp3350.teachreach.objects.Course;

public
interface ICourse
{

    String getCourseCode();

    void setCourseCode(String courseCode);

    String getCourseName();

    void setCourseName(String courseName);

    boolean equals(Course otherCourse);

    String toString();
}
