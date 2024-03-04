package comp3350.teachreach.objects;

import java.util.ArrayList;

public class Tutor extends User {
    private int tutorID;
    private ArrayList<Course> tutoredCourses;
    private double hourlyRate;
    private int reviewSum;
    private int reviewCount;
    private ArrayList<String> preferredLocations;
    private boolean[][] availability;
    private boolean[][] preferredAvailability;

    public Tutor(String name, String pronouns, String major) {
        super(name, pronouns, major);
        this.tutoredCourses = new ArrayList<Course>();
        this.preferredLocations = new ArrayList<String>();
        this.availability = new boolean[7][24];
        this.hourlyRate = 10; // Arbitrary default
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.tutorID = -1;
    }

    public Tutor(String name, String pronouns, String major, int tutorID) {
        super(name, pronouns, major);
        this.tutoredCourses = new ArrayList<Course>();
        this.preferredLocations = new ArrayList<String>();
        this.availability = new boolean[7][24];
        this.hourlyRate = 10; // Arbitrary default
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.tutorID = tutorID;
    }

    public Tutor(String name, String pronouns, String major, double hourlyRate) {
        super(name, pronouns, major);
        tutoredCourses = new ArrayList<Course>();
        this.preferredLocations = new ArrayList<String>();
        this.availability = new boolean[7][24];
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.hourlyRate = hourlyRate;
        this.tutorID = -1;
    }

    public Tutor(String name, String pronouns, String major, double hourlyRate, int tutorID) {
        super(name, pronouns, major);
        tutoredCourses = new ArrayList<Course>();
        this.preferredLocations = new ArrayList<String>();
        this.availability = new boolean[7][24];
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.hourlyRate = hourlyRate;
        this.tutorID = tutorID;
    }

    public ArrayList<Course> getCourses() {
        return tutoredCourses;
    }

    public Tutor addCourse(Course course) {
        this.tutoredCourses.add(course);
        return this;
    }

    public ArrayList<String> getLocations() {
        return this.preferredLocations;
    }

    public void setLocations(String preferredLocations) {
        this.preferredLocations.add(preferredLocations);
    }

    public void clearTutoredCourses() {
        tutoredCourses.clear();
    }

    public double getHourlyRate() {
        return this.hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void addReview(int score) {
        this.reviewSum += score;
        this.reviewCount++;
    }

    public void setReviewSum(int reviewSum) {
        this.reviewSum = reviewSum;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public float getRating() {
        return this.reviewCount > 0 ? ((float) this.reviewSum / (float) this.reviewCount) : 0;
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

    public void setFullAvailability(boolean[][] availability) {
        this.availability = availability;
    }

    public void setFullPrefAvailability(boolean[][] availability) {
        this.preferredAvailability = availability;
    }

    public int getReviewCount() {
        return this.reviewCount;
    }
}
