package comp3350.teachreach.logic.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.ITutor;

public class TutorProfile implements ITutorProfile {

    private final ITutor theTutor;
    private final ITutorPersistence tutorsDataAccess;

    public TutorProfile(
            ITutor theTutor,
            ITutorPersistence tutors) {

        this.theTutor = theTutor;
        this.tutorsDataAccess = tutors;
    }

    public TutorProfile(
            String tutorEmail,
            ITutorPersistence tutors) throws NoSuchElementException {

        this.tutorsDataAccess = tutors;
        this.theTutor =
                tutorsDataAccess
                        .getTutorByEmail(tutorEmail)
                        .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String getTutorEmail() {
        return this.theTutor.getOwner().getEmail();
    }

    @Override
    public double getHourlyRate() {
        return theTutor.getHourlyRate();
    }

    @Override
    public double getAvgReview() {
        return theTutor.getReviewCount() > 0 ?
                ((double) theTutor.getReviewTotal() / (double) theTutor.getReviewCount()) : 0;
    }

    @Override
    public ArrayList<Course> getCourses() {
        return theTutor.getCourses();
    }

    @Override
    public ArrayList<String> getPreferredLocations() {
        return theTutor.getPreferredLocations();
    }

    @Override
    public boolean[][] getPreferredAvailability() {
        return this.theTutor.getPreferredAvailability();
    }

    @Override
    public boolean[][] getAvailability() {
        return this.theTutor.getAvailability();
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
    public ITutorProfile setPreferredAvailability(boolean[][] newPreference) {
        for (int week = 0; week < newPreference.length; week++) {
            for (int hour = 0; hour < newPreference[week].length; hour++) {
                this.theTutor.setPreferredAvailability(
                        week, hour, newPreference[week][hour]);
            }
        }
        return this;
    }

    @Override
    public ITutorProfile setAvailability(boolean[][] newAvailability) {
        for (int week = 0; week < newAvailability.length; week++) {
            for (int hour = 0; hour < newAvailability[week].length; hour++) {
                this.theTutor.setAvailability(
                        week, hour,
                        newAvailability[week][hour]
                                && theTutor.getPreferredAvailability()[week][hour]);
            } // Assuming true is Available, Availability is
        }     // Preferred Availability - Booked Session
        return this;
    }

    @Override
    public void updateTutor() {
        tutorsDataAccess.updateTutor(theTutor);
    }
}
