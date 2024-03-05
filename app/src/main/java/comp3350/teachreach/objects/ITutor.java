package comp3350.teachreach.objects;

import java.util.List;

public interface ITutor extends IUser {

    List<ICourse> getCourses();

    List<String> getPreferredLocations();

    ITutor setPreferredLocations(List<String> preferredLocations);

    int getReviewTotalSum();

    int getReviewCount();

    ITutor setReviewCount(int count);

    double getHourlyRate();

    ITutor setHourlyRate(double hourlyRate);

    boolean equals(ITutor other);

    ITutor addCourse(ICourse course);

    ITutor clearTutoredCourses();

    ITutor addReview(int score);

    ITutor setReviewTotal(int score);

    ITutor clearReviews();

    ITutor addPreferredLocation(String preferredLocation);

    List<List<TimeSlice>> getPreferredAvailability();

    ITutor setPreferredAvailability(final List<List<TimeSlice>> weeklyAvailability);

    List<ISession> getFutureSessions();
}
