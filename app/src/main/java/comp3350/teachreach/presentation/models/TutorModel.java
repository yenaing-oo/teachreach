package comp3350.teachreach.presentation.models;

import java.util.Locale;

import comp3350.teachreach.objects.ITutor;

public class TutorModel {
    String name;
    String rating;
    String hourlyRate;
    String numReviews;
    String email;

    public TutorModel(String name, String rating, String hourlyRate) {
        this.name = name;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
    }

    public TutorModel(ITutor tutor) {
        this.name = tutor.getName();
        this.email = tutor.getOwner().getEmail();
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

    public String getEmail() {
        return this.email;
    }
}