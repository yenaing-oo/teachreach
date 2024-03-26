package comp3350.teachreach.presentation.utils;

import java.util.List;
import java.util.Locale;

import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ICourse;

public
class TutorProfileFormatter
{
    private final ITutorProfileHandler tutorProfileHandler;

    public
    TutorProfileFormatter(ITutorProfileHandler tutorProfile)
    {
        this.tutorProfileHandler = tutorProfile;
    }

    public
    String getName()
    {
        return tutorProfileHandler.getUserName();
    }

    public
    String getRating()
    {
        String rating     = String.format(Locale.US, "%.1f", tutorProfileHandler.getAvgReview());
        String numReviews = Integer.toString(tutorProfileHandler.getReviewCount());
        return String.format("Rating: %s⭐ (%s)", rating, numReviews);
    }

    public
    String getHourlyRate()
    {
        String hourlyRate = String.format(Locale.US, "%.2f", tutorProfileHandler.getHourlyRate());
        return "$" + hourlyRate + "/hr";
    }

    public
    String getCourses()
    {
        List<ICourse> courses    = tutorProfileHandler.getCourses();
        StringBuilder coursesStr = new StringBuilder();
        for (ICourse course : courses) {
            coursesStr.append(course.toString()).append("\n");
        }
        return coursesStr.toString();
    }
}
