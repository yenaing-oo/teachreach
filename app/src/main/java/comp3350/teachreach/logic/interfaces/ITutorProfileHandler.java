package comp3350.teachreach.logic.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public
interface ITutorProfileHandler extends IUserProfileHandler
{
    double getHourlyRate();

    ITutorProfileHandler setHourlyRate(double hourlyRate);

    double getAvgReview();

    int getReviewCount();

    int getReviewSum();

    List<ICourse> getCourses();

    List<String> getPreferredLocations();

    ITutorProfileHandler addReview(int score);

    ITutorProfileHandler addCourse(String courseCode, String courseName);

    ITutorProfileHandler removeCourse(String courseCode);

    ITutorProfileHandler addPreferredLocation(String preferredLocation);

    ITutorProfileHandler addPreferredLocations(List<String> preferredLocations);

    ITutor updateTutorProfile();
}
