package comp3350.teachreach.logic.profile;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.objects.Course;

public interface ITutorProfile {
    String getTutorEmail();
    double getHourlyRate();
    double getAvgReview();
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
    void updateTutor(); // NEED TO BE CALLED AFTER CHANGE OF PROFILE
}
