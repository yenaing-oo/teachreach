package comp3350.teachreach.objects;

import comp3350.teachreach.objects.interfaces.ICourse;

public
class Course implements ICourse
{
    private String courseCode;
    private String courseName;

    public
    Course(String courseCode, String courseName)
    {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    @Override
    public
    String getCourseCode()
    {
        return this.courseCode;
    }

    @Override
    public
    Course setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
        return this;
    }

    @Override
    public
    String getCourseName()
    {
        return courseName;
    }

    @Override
    public
    Course setCourseName(String courseName)
    {
        this.courseName = courseName;
        return this;
    }

    @Override
    public
    boolean equals(ICourse otherCourse)
    {
        return this.courseCode.equalsIgnoreCase(otherCourse.getCourseCode());
    }

    @Override
    public
    String toString()
    {
        return String.format("%s: %s", this.courseCode, this.courseName);
    }
}
