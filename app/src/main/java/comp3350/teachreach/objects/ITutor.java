package comp3350.teachreach.objects;

public interface ITutor {

    public ArrayList<Course> getCourses();

    public Tutor addCourse(Course course);

    public ArrayList<String> getLocations();

    public void setLocations(String preferredLocations);

    public void clearTutoredCourses();

    public double getHourlyRate();

    public void setHourlyRate(float hourlyRate);

    public void addReview(int score);

    public float getRating();

    public void clearReviews();

    public boolean[][] getAvailability();

    public void setAvailability(int day, int hour, boolean avail);

    public int getReviewCount();
}
