package comp3350.teachreach.objects;

import java.util.ArrayList;

public interface ITutor extends IUser {

    public ArrayList<Course> getCourses();

    public ITutor addCourse(Course course);

    public ArrayList<String> getPreferredLocations();

    public ITutor setPreferredLocations(String preferredLocations);

    public ITutor clearTutoredCourses();

    public double getHourlyRate();

    public ITutor setHourlyRate(double hourlyRate);

    public ITutor addReview(int score);

    public ITutor setReviewCount(int count);

    public ITutor setReviewTotal(int score);

    public int getReviewTotal();

    public float getRating();

    public ITutor clearReviews();

    public boolean[][] getAvailability();

    public ITutor setAvailability(int day, int hour, boolean avail);

    public ITutor renewAvailability(boolean[][] newAvailability);

    public boolean[][] getPreferredAvailability();

    public ITutor renewPreferredAvailability(boolean[][] newPreferredAvailability);

    public int getReviewCount();
}
