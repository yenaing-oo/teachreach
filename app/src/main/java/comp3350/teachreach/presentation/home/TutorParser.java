package comp3350.teachreach.presentation.home;

import java.util.Locale;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.logic.profile.ITutorProfile;
import comp3350.teachreach.logic.profile.TutorProfile;
import comp3350.teachreach.objects.ITutor;

public class TutorParser {
    private final ITutorProfile tutorProfile;

    public TutorParser(ITutor tutor) {
        this.tutorProfile = new TutorProfile(tutor, Server.getTutorDataAccess());
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

    public String getEmail() {
        return tutorProfile.getUserEmail();
    }
}
