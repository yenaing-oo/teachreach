package comp3350.teachreach.objects;

import java.util.ArrayList;

public class Tutor extends User {
    private ArrayList<Course> tutoredCourses;
    private double pricePerHour;
    private int reviewSum;
    private int reviewCount;
    private ArrayList<String> preferredLocations;
    private final boolean[][] availability;

    public Tutor(String name, String pronouns, String major, String email, String password) {
        super(name, pronouns, major, email, password);
        this.tutoredCourses = new ArrayList<Course>();
        this.preferredLocations = new ArrayList<String>();
        this.availability = new boolean[7][24];
        this.pricePerHour = 10; //Arbitrary default
        this.reviewSum = 0;
        this.reviewCount = 0;

    }

    public Tutor(String name, String pronouns, String major, String email,
                 String password, double pricePerHour) {
        super(name, pronouns, major, email, password);
        tutoredCourses = new ArrayList<Course>();
        this.preferredLocations = new ArrayList<String>();
        this.availability = new boolean[7][24];
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.pricePerHour = pricePerHour;

    }

    public ArrayList<Course> getCourses() {
        return tutoredCourses;
    }

    public void setCourses(ArrayList<Course> tutoredCourses) {
        this.tutoredCourses = tutoredCourses;
    }

    public ArrayList<String> getLocations() {
        return this.preferredLocations;
    }

    public void setLocations(ArrayList<String> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    public void clearTutoredCourses() {
        tutoredCourses.clear();
    }

    public double getPricePerHour() {
        return this.pricePerHour;
    }

    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void addReview(int score) {
        this.reviewSum += score;
        this.reviewCount++;
    }

    public float getRating() {
        return (float) this.reviewSum / (float) this.reviewCount;
    }

    public void clearReviews() {
        this.reviewSum = 0;
        this.reviewCount = 0;
    }

    public boolean[][] getAvailability() {
        return this.availability;
    }

    public void setAvailability(int day, int hour, boolean avail) {
        this.availability[day][hour] = avail;
    }
}
