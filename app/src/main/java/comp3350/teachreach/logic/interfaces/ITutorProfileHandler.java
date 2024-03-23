package comp3350.teachreach.logic.interfaces;

import java.util.List;

import comp3350.teachreach.logic.exceptions.input.InvalidInputException;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public interface ITutorProfileHandler extends IUserProfileHandler
{
    double getHourlyRate();

    ITutorProfileHandler setHourlyRate(double hourlyRate);

    double getAvgReview();

    int getReviewCount();

    int getReviewSum();

    List<ICourse> getCourses();

    List<String> getCourseCodeList();

    List<String> getCourseDescriptionList();

    List<String> getPreferredLocations();

    ITutorProfileHandler addReview(int score);

    ITutorProfileHandler addCourse(String courseCode, String courseName)
            throws InvalidInputException;

    ITutorProfileHandler removeCourse(String courseCode);

    ITutorProfileHandler addPreferredLocation(String preferredLocation)
            throws InvalidInputException;

    ITutor updateTutorProfile();
}
