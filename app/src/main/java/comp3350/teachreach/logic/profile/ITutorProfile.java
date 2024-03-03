package comp3350.teachreach.logic.profile;

import java.time.DayOfWeek;
import java.util.List;

import comp3350.teachreach.objects.ICourse;
import comp3350.teachreach.objects.TimeSlice;

public interface ITutorProfile extends IUserProfile {
    double getHourlyRate();

    ITutorProfile setHourlyRate(double hourlyRate);

    double getAvgReview();

    int getReviewCount();

    int getReviewSum();

    List<ICourse> getCourses();

    List<String> getPreferredLocations();

    List<List<TimeSlice>> getPreferredAvailability();

    List<TimeSlice> getAvailableTimeSlotOfRange(
            int startYear, int startMonth, int startDay,
            int startHour, int startMinute,
            int endYear, int endMonth, int endDay,
            int endHour, int endMinute);

    ITutorProfile addReview(int score);

    ITutorProfile addCourse(String courseCode, String courseName);

    ITutorProfile removeCourse(String courseCode);

    ITutorProfile addPreferredLocation(String preferredLocation);

    ITutorProfile addPreferredLocations(List<String> preferredLocations);

    ITutorProfile resetPreferredAvailability();

    ITutorProfile setPreferredAvailability(
            int startYear, int startMonth, int startDay,
            int startHour, int startMinute,
            int endYear, int endMonth, int endDay,
            int endHour, int endMinute,
            List<DayOfWeek> daysOfWeek);

    void updateUserProfile(); // NEED TO BE CALLED AFTER CHANGE OF PROFILE
}
