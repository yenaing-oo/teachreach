package comp3350.teachreach.presentation.utils;

import java.util.List;
import java.util.Locale;

import comp3350.teachreach.logic.interfaces.ITutorProfile;
import comp3350.teachreach.objects.interfaces.ICourse;

public class TutorProfileFormatter {
    private final ITutorProfile tutorProfile;

    public TutorProfileFormatter(ITutorProfile tutorProfile) {
        this.tutorProfile = tutorProfile;
    }

    public String getName() {
        return tutorProfile.getUserName();
    }

    public String getRating() {
        String rating = String.format(Locale.US, "%.1f", tutorProfile.getAvgReview());
        String numReviews = Integer.toString(tutorProfile.getReviewCount());
        return String.format("Rating: %s⭐ (%s)", rating, numReviews);
    }

    public String getHourlyRate() {
        String hourlyRate = String.format(Locale.US, "%.2f", tutorProfile.getHourlyRate());
        return "$" + hourlyRate + "/hr";
    }

    public String getCourses() {
        List<ICourse> courses = tutorProfile.getCourses();
        StringBuilder coursesStr = new StringBuilder();
        for (ICourse course : courses) {
            coursesStr.append(course.toString()).append("\n");
        }
        return coursesStr.toString();
    }
}
