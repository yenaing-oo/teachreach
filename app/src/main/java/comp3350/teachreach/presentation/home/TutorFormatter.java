package comp3350.teachreach.presentation.home;

import java.util.Locale;

import comp3350.teachreach.objects.Tutor;

public class TutorFormatter {
    String name;
    String rating;
    String hourlyRate;
    String numReviews;

    public TutorFormatter(Tutor tutor) {
        this.name = tutor.getName();
        this.rating = String.format(Locale.US, "%.1f", tutor.getRating());
        this.hourlyRate = String.format(Locale.US, "%.2f", tutor.getHourlyRate());
        this.numReviews = Integer.toString(tutor.getReviewCount());
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return String.format("Rating: %s⭐ (%s)", rating, numReviews);
    }

    public String getHourlyRate() {
        return "$" + hourlyRate + "/hr";
    }
}