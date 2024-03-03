package comp3350.teachreach.presentation.home;

import java.util.Locale;

import comp3350.teachreach.objects.ITutor;

public class TutorParser {
    private final ITutor tutor;

    public TutorParser(ITutor tutor) {
        this.tutor = tutor;
    }

    public String getName() {
        return tutor.getName();
    }

    public String getRating() {
        String rating = String.format(Locale.US, "%.1f", tutor.getRating());
        String numReviews = Integer.toString(tutor.getReviewCount());
        return String.format("Rating: %s⭐ (%s)", rating, numReviews);
    }

    public String getHourlyRate() {
        String hourlyRate = String.format(Locale.US, "%.2f", tutor.getHourlyRate());
        return "$" + hourlyRate + "/hr";
    }

    public String getEmail() {
        return tutor.getOwner().getEmail();
    }
}
