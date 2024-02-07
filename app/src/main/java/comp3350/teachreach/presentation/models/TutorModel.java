package comp3350.teachreach.presentation.models;

public class TutorModel {
    String name;
    String rating;
    String hourlyRate;

    public TutorModel(String name, String rating, String hourlyRate) {
        this.name = name;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
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