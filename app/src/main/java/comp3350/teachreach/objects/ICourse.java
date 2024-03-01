package comp3350.teachreach.objects;

public interface ICourse {

    public String getCourseCode();

    public void setCourseCode(String courseCode);

    public String getCourseName();

    public void setCourseName(String courseName);

    public boolean equals(Course otherCourse);

    @NonNull
    @Override
    public String toString();
}
