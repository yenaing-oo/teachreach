package comp3350.teachreach.objects;

import java.util.List;

public interface ITutor extends IUser {

     List<ICourse> getCourses();
     List<String> getPreferredLocations();
     int getReviewTotalSum();
     int getReviewCount();
     double getHourlyRate();
     boolean equals(ITutor other);

     ITutor addCourse(ICourse course);
     ITutor clearTutoredCourses();
     ITutor setHourlyRate(double hourlyRate);
     ITutor addReview(int score);
     ITutor setReviewCount(int count);
     ITutor setReviewTotal(int score);
     ITutor clearReviews();
     ITutor setPreferredAvailability(final List<List<TimeSlice>> weeklyAvailability);
     ITutor addPreferredLocation(String preferredLocation);
     ITutor setPreferredLocations(List<String> preferredLocations);
     List<List<TimeSlice>> getPreferredAvailability();
     List<ISession> getFutureSessions();
}
