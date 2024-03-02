package comp3350.teachreach.logic.profile;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.objects.Course;

public interface ITutorProfile extends IUserProfile {
    double getHourlyRate();
    double getAvgReview();
    int getReviewCount();
    int getReviewSum();
    ArrayList<Course> getCourses();
    ArrayList<String> getPreferredLocations();
    boolean[][] getPreferredAvailability();
    boolean[][] getAvailability();
    ITutorProfile setHourlyRate(double hourlyRate);
    ITutorProfile addReview(int score);
    ITutorProfile addCourse(String courseCode, String courseName);
    ITutorProfile removeCourse(String courseCode);
    ITutorProfile addPreferredLocation(String preferredLocation);
    ITutorProfile addPreferredLocations(List<String> preferredLocations);
    ITutorProfile setPreferredAvailability(boolean[][] newPreference);
    ITutorProfile setAvailability(boolean[][] newAvailability);
    void updateUserProfile(); // NEED TO BE CALLED AFTER CHANGE OF PROFILE
}
