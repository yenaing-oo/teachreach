package comp3350.teachreach.logic.interfaces;

import java.time.DayOfWeek;
import java.util.List;

import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ICourse;

public
interface ITutorProfileHandler
{
    double getHourlyRate();

    ITutorProfileHandler setHourlyRate(double hourlyRate);

    double getAvgReview();

    int getReviewCount();

    int getReviewSum();

    List<ICourse> getCourses();

    List<String> getPreferredLocations();

    List<List<TimeSlice>> getPreferredAvailability();

    List<TimeSlice> getAvailableTimeSlotOfRange(int startYear,
                                                int startMonth,
                                                int startDay,
                                                int startHour,
                                                int startMinute,
                                                int endYear,
                                                int endMonth,
                                                int endDay,
                                                int endHour,
                                                int endMinute);

    ITutorProfileHandler addReview(int score);

    ITutorProfileHandler addCourse(String courseCode, String courseName);

    ITutorProfileHandler removeCourse(String courseCode);

    ITutorProfileHandler addPreferredLocation(String preferredLocation);

    ITutorProfileHandler addPreferredLocations(List<String> preferredLocations);

    ITutorProfileHandler resetPreferredAvailability();

    ITutorProfileHandler setPreferredAvailability(int startYear,
                                                  int startMonth,
                                                  int startDay,
                                                  int startHour,
                                                  int startMinute,
                                                  int endYear,
                                                  int endMonth,
                                                  int endDay,
                                                  int endHour,
                                                  int endMinute,
                                                  List<DayOfWeek> daysOfWeek);

    void updateUserProfile(); // NEED TO BE CALLED AFTER CHANGE OF PROFILE
}
