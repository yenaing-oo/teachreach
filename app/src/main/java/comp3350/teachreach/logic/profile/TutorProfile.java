package comp3350.teachreach.logic.profile;

import java.time.DayOfWeek;
import java.util.List;
import java.util.NoSuchElementException;

import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.ICourse;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.TimeSlice;

public class TutorProfile implements ITutorProfile {

    private final ITutor theTutor;
    private final ITutorPersistence tutorsDataAccess;
    private final AvailabilityManager availabilityManager;

    public TutorProfile(
            ITutor theTutor,
            ITutorPersistence tutors) {

        this.theTutor = theTutor;
        this.tutorsDataAccess = tutors;
        this.availabilityManager = new AvailabilityManager(theTutor);
    }

    public TutorProfile(
            String tutorEmail,
            ITutorPersistence tutors) throws NoSuchElementException {

        this.tutorsDataAccess = tutors;
        this.theTutor = tutorsDataAccess
                .getTutorByEmail(tutorEmail)
                .orElseThrow(NoSuchElementException::new);
        this.availabilityManager = new AvailabilityManager(theTutor);
    }

    @Override
    public String getUserEmail() {
        return this.theTutor.getOwner().getEmail();
    }

    @Override
    public String getUserName() {
        return this.theTutor.getName();
    }

    @Override
    public String getUserPronouns() {
        return this.theTutor.getPronouns();
    }

    @Override
    public String getUserMajor() {
        return this.theTutor.getMajor();
    }

    @Override
    public IAccount getUserAccount() {
        return this.theTutor.getOwner();
    }

    @Override
    public IUserProfile setUserName(String name) {
        this.theTutor.setName(name);
        return this;
    }

    @Override
    public IUserProfile setUserPronouns(String pronouns) {
        this.theTutor.setPronouns(pronouns);
        return this;
    }

    @Override
    public IUserProfile setUserMajor(String major) {
        this.theTutor.setMajor(major);
        return this;
    }

    @Override
    public double getHourlyRate() {
        return theTutor.getHourlyRate();
    }

    @Override
    public double getAvgReview() {
        return theTutor.getReviewCount() > 0 ?
                ((double) theTutor.getReviewTotalSum() / (double) theTutor.getReviewCount()) : 0;
    }

    @Override
    public int getReviewCount() {
        return this.theTutor.getReviewCount();
    }

    @Override
    public int getReviewSum() {
        return this.theTutor.getReviewTotalSum();
    }

    @Override
    public List<ICourse> getCourses() {
        return theTutor.getCourses();
    }

    @Override
    public List<String> getPreferredLocations() {
        return theTutor.getPreferredLocations();
    }

    @Override
    public List<List<TimeSlice>> getPreferredAvailability() {
        return this.availabilityManager.getWeeklyAvailability();
    }

    @Override
    public List<TimeSlice> getAvailableTimeSlotOfRange(
            int startYear, int startMonth, int startDay,
            int startHour, int startMinute,
            int endYear, int endMonth, int endDay,
            int endHour, int endMinute) {

        return this.availabilityManager.getAvailableTimeSlotOfRange(
                startYear, startMonth, startDay, startHour, startMinute,
                endYear, endMonth, endDay, endHour, endMinute);
    }

    @Override
    public ITutorProfile setHourlyRate(double hourlyRate) {
        this.theTutor.setHourlyRate(hourlyRate);
        return this;
    }

    @Override
    public ITutorProfile addReview(int score) {
        this.theTutor.addReview(score);
        return this;
    }

    @Override
    public ITutorProfile addCourse(String courseCode, String courseName) {
        if (this.theTutor.getCourses().stream().noneMatch(
                course -> course.getCourseCode().equals(courseCode))) {
            this.theTutor.addCourse(new Course(courseCode, courseName));
        }
        return this;
    }

    @Override
    public ITutorProfile removeCourse(String courseCode) {
        this.theTutor.getCourses().removeIf(
                course -> course.getCourseCode().equals(courseCode));
        return this;
    }

    @Override
    public ITutorProfile addPreferredLocation(String preferredLocation) {
        if (this.theTutor.getPreferredLocations().stream().noneMatch(
                location -> location.equals(preferredLocation))) {
            this.theTutor.addPreferredLocation(preferredLocation);
        }
        return this;
    }

    @Override
    public ITutorProfile addPreferredLocations(List<String> preferredLocations) {
        preferredLocations.forEach(this::addPreferredLocation);
        return this;
    }

    @Override
    public ITutorProfile resetPreferredAvailability() {
        this.availabilityManager.clearWeeklyAvailability();
        return this;
    }

    @Override
    public ITutorProfile setPreferredAvailability(int startYear,
                                                  int startMonth,
                                                  int startDay,
                                                  int startHour,
                                                  int startMinute,
                                                  int endYear,
                                                  int endMonth,
                                                  int endDay,
                                                  int endHour,
                                                  int endMinute,
                                                  List<DayOfWeek> daysOfWeek) {
        this.availabilityManager.addWeeklyAvailability(
                startYear, startMonth, startDay, startHour, startMinute,
                endYear, endMonth, endDay, endHour, endMinute, daysOfWeek);
        return this;
    }

    @Override
    public void updateUserProfile() {
        tutorsDataAccess.updateTutor(theTutor);
    }
}
