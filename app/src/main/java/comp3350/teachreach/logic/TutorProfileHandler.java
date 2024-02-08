package comp3350.teachreach.logic;

import android.accounts.Account;

import java.util.ArrayList;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;

public class TutorProfileHandler {
    private IAccountPersistence dataAccessAccounts;
    private Tutor tutorAccount;

    public TutorProfileHandler(Tutor account) {
        dataAccessAccounts = Server.getAccounts();
        tutorAccount = account;
    }

    public ArrayList<Course> getTutoredCourses() {
        return tutorAccount.getCourses();
    }

    public double getTutorHourlyRate() {
        return tutorAccount.getHourlyRate();
    }

    public float getTutorRating() {
        return tutorAccount.getRating();
    }

    public boolean[][] getTutorAvailability() {
        return tutorAccount.getAvailability();
    }

    public void clearTutoredCourses(ArrayList<Course> tutorCourses) {
        tutorAccount.clearTutoredCourses();
        dataAccessAccounts.updateTutor(tutorAccount);
    }

    public void setTutorHourlyRate(float newRate) {
        tutorAccount.setHourlyRate(newRate);
        dataAccessAccounts.updateTutor(tutorAccount);
    }

    public void addNewReview(int score) {
        tutorAccount.addReview(score);
        dataAccessAccounts.updateTutor(tutorAccount);
    }

    public boolean setAvailability(int dayOfWeek, int hourOfDay,
                                   boolean avail) {
        boolean success = false;
        if (isValidDayOfWeek(dayOfWeek) && isValidHourOfDay(hourOfDay)) {
            tutorAccount.setAvailability(dayOfWeek, hourOfDay, avail);
            dataAccessAccounts.updateTutor(tutorAccount);
            success = true;
        }
        return success;
    }

    private static boolean isValidDayOfWeek(int dayOfWeek) {
        return dayOfWeek >= 0 && dayOfWeek <= 6;
    }

    private static boolean isValidHourOfDay(int hourOfDay) {
        return hourOfDay >= 0 && hourOfDay <= 23;
    }
}
