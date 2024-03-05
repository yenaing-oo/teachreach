package comp3350.teachreach.objects;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.ITutor;

public class Tutor extends User implements ITutor {
    private List<ICourse> tutoredCourses;
    private double hourlyRate;
    private int reviewSum;
    private int reviewCount;
    private List<String> preferredLocations;
    private List<ISession> pendingSessions;
    private List<ISession> expiredSessions;
    private List<ISession> approvedFutureSessions;
    private List<List<TimeSlice>> preferredAvailability;

    public Tutor(String email,
                 String name, String pronouns, String major) {
        super(email, name, pronouns, major);
        this.tutoredCourses = new ArrayList<>();
        this.preferredLocations = new ArrayList<>();
        this.preferredAvailability = new ArrayList<>(7);
        this.preferredAvailability.replaceAll(ignored -> new ArrayList<>());
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.pendingSessions = new ArrayList<>();
        this.expiredSessions = new ArrayList<>();
        this.approvedFutureSessions = new ArrayList<>();
    }

    public Tutor(String email,
                 String name, String pronouns, String major, double hourlyRate) {
        super(email, name, pronouns, major);
        this.tutoredCourses = new ArrayList<>();
        this.preferredLocations = new ArrayList<>();
        this.preferredAvailability = new ArrayList<>(7);
        this.preferredAvailability.replaceAll(ignored -> new ArrayList<>());
        this.reviewSum = 0;
        this.reviewCount = 0;
        this.hourlyRate = hourlyRate;
        this.pendingSessions = new ArrayList<>();
        this.expiredSessions = new ArrayList<>();
        this.approvedFutureSessions = new ArrayList<>();
    }

    @Override
    public List<ICourse> getCourses() {
        return tutoredCourses;
    }

    @Override
    public Tutor addCourse(ICourse course) {
        this.tutoredCourses.add(course);
        return this;
    }

    @Override
    public List<String> getPreferredLocations() {
        return this.preferredLocations;
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

    @Override
    public boolean equals(ITutor other) {
        return this.getEmail().equals(other.getEmail());
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
    public int getReviewTotalSum() {
        return this.reviewSum;
    }

    public Tutor clearReviews() {
        this.reviewSum = 0;
        this.reviewCount = 0;
        return this;
    }

    public List<ISession> getFutureSessions() {
        return this.approvedFutureSessions;
    }

    @Override
    public List<List<TimeSlice>> getPreferredAvailability() {
        return this.preferredAvailability;
    }

    @Override
    public Tutor setPreferredAvailability(List<List<TimeSlice>> weeklyAvailability) {
        this.preferredAvailability = weeklyAvailability;
        return this;
    }

    public int getReviewCount() {
        return this.reviewCount;
    }

    @Override
    public ITutor addPreferredLocation(String preferredLocation) {
        this.preferredLocations.add(preferredLocation);
        return this;
    }

    @Override
    public ITutor setPreferredLocations(List<String> preferredLocations) {
        this.preferredLocations = preferredLocations;
        return this;
    }
}
