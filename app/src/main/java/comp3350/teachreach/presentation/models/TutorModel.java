package comp3350.teachreach.presentation.models;

import java.util.Locale;

import comp3350.teachreach.objects.Tutor;

public class TutorModel {
    String name;
    String rating;
    String hourlyRate;

    public TutorModel(String name, String rating, String hourlyRate) {
        this.name = name;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
    }

    public TutorModel(Tutor tutor) {
        this.name = tutor.getName();
        this.rating = String.format(Locale.US, "%.1f", tutor.getRating());
        this.hourlyRate = String.format(Locale.US, "%.2f", tutor.getPricePerHour());
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }
}