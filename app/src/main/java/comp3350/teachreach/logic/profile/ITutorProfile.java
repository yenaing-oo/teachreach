package comp3350.teachreach.logic.profile;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.objects.ICourse;

public interface ITutorProfile extends IUserProfile {
    double getHourlyRate();

    ITutorProfile setHourlyRate(double hourlyRate);

    double getAvgReview();

    int getReviewCount();

    int getReviewSum();

    ArrayList<ICourse> getCourses();

    ArrayList<String> getPreferredLocations();

    boolean[][] getPreferredAvailability();

    ITutorProfile setPreferredAvailability(boolean[][] newPreference);

    boolean[][] getAvailability();

    ITutorProfile setAvailability(boolean[][] newAvailability);

    ITutorProfile addReview(int score);

    ITutorProfile addCourse(String courseCode, String courseName);

    ITutorProfile removeCourse(String courseCode);

    ITutorProfile addPreferredLocation(String preferredLocation);

    ITutorProfile addPreferredLocations(List<String> preferredLocations);

    void updateUserProfile(); // NEED TO BE CALLED AFTER CHANGE OF PROFILE
}
