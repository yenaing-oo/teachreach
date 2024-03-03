package comp3350.teachreach.objects;

import java.util.ArrayList;

public interface ITutor extends IUser {

    ArrayList<ICourse> getCourses();

    ITutor addCourse(ICourse course);

    ArrayList<String> getPreferredLocations();

    ITutor setPreferredLocations(String preferredLocations);

    ITutor clearTutoredCourses();

    double getHourlyRate();

    ITutor setHourlyRate(double hourlyRate);

    ITutor addReview(int score);

    ITutor setReviewTotal(int score);

    int getReviewTotalSum();

    float getRating();

    ITutor clearReviews();

    boolean[][] getAvailability();

    ITutor setAvailability(int day, int hour, boolean avail);

    ITutor setPreferredAvailability(int day, int hour, boolean avail);

    ITutor renewAvailability(boolean[][] newAvailability);

    boolean[][] getPreferredAvailability();

    ITutor renewPreferredAvailability(boolean[][] newPreferredAvailability);

    int getReviewCount();

    ITutor setReviewCount(int count);

    ITutor addPreferredLocation(String preferredLocation);
}
