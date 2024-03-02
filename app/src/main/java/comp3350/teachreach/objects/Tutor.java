package comp3350.teachreach.objects;

import java.util.ArrayList;

public class Tutor extends User implements ITutor {
    private ArrayList<Course> tutoredCourses;
    private double hourlyRate;
    private int reviewSum;
    private int reviewCount;
    private ArrayList<String> preferredLocations;
    private boolean[][] availability;
    private boolean[][] preferredAvailability;

    public Tutor(String name,
                 String pronouns,
                 String major,
                 IAccount parentAccount) {
        super(name, pronouns, major, parentAccount);
        this.tutoredCourses = new ArrayList<>();
        this.preferredLocations = new ArrayList<>();
        this.availability = new boolean[7][24];
        this.reviewSum = 0;
        this.reviewCount = 0;
    }

    public Tutor(String name,
                 String pronouns,
                 String major,
                 IAccount parentAccount,
                 double hourlyRate) {
        super(name, pronouns, major, parentAccount);
        this.tutoredCourses = new ArrayList<>();
        this.preferredLocations = new ArrayList<>();
        this.availability = new boolean[7][24];
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.hourlyRate = hourlyRate;
    }

    public ArrayList<Course> getCourses() {
        return tutoredCourses;
    }

    public Tutor addCourse(Course course) {
        this.tutoredCourses.add(course);
        return this;
    }

    public ArrayList<String> getPreferredLocations() {
        return this.preferredLocations;
    }

    public Tutor setPreferredLocations(String preferredLocations) {
        this.preferredLocations.add(preferredLocations);
        return this;
    }

    public Tutor clearTutoredCourses() {
        tutoredCourses.clear();
        return this;
    }

    public double getHourlyRate() {
        return this.hourlyRate;
    }

    public Tutor setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public Tutor addReview(int score) {
        this.reviewSum += score;
        this.reviewCount++;
        return this;
    }

    @Override
    public ITutor setReviewCount(int count) {
        this.reviewCount = count;
        return this;
    }

    @Override
    public ITutor setReviewTotal(int score) {
        this.reviewSum = score;
        return null;
    }

    @Override
    public int getReviewTotal() {
        return this.reviewSum;
    }

    public float getRating() {
        return this.reviewCount > 0 ? ((float) this.reviewSum / (float) this.reviewCount) : 0;
    }

    public Tutor clearReviews() {
        this.reviewSum = 0;
        this.reviewCount = 0;
        return this;
    }

    public boolean[][] getAvailability() {
        return this.availability;
    }

    public Tutor setAvailability(int day, int hour, boolean avail) {
        this.availability[day][hour] = avail;
        return this;
    }

    @Override
    public ITutor renewAvailability(boolean[][] newAvailability) {
        return null;
    }

    public boolean[][] getPreferredAvailability() {
        return this.preferredAvailability;
    }

    @Override
    public ITutor renewPreferredAvailability(boolean[][] newPreferredAvailability) {
        return null;
    }

    public Tutor setPreferredAvailability(int day, int hour, boolean avail) {
        this.preferredAvailability[day][hour] = avail;
        return this;
    }

    public int getReviewCount() {
        return this.reviewCount;
    }
}
