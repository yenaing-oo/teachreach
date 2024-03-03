package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.data.stubs.AccountStub;
import comp3350.teachreach.data.stubs.StudentStub;
import comp3350.teachreach.data.stubs.TutorStub;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.account.AccountCreatorException;
import comp3350.teachreach.logic.account.CredentialHandler;
import comp3350.teachreach.logic.account.ICredentialHandler;
import comp3350.teachreach.logic.profile.ITutorProfile;
import comp3350.teachreach.logic.profile.TutorProfile;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.ICourse;
import comp3350.teachreach.objects.ITutor;

public class TutorProfileTest {

    private ITutorProfile theTutorProfile = null;
    private ITutorPersistence tutorsDataAccess = null;

    @Before
    public void setUp() throws AccountCreatorException {
        System.out.println("Starting a new test for TutorProfile");
        IAccountPersistence accountsDataAccess = new AccountStub();
        IStudentPersistence studentsDataAccess = new StudentStub(accountsDataAccess);
        tutorsDataAccess = new TutorStub(accountsDataAccess);
        ICredentialHandler credentialHandler = new CredentialHandler(accountsDataAccess);

        AccountCreator accountCreator = new AccountCreator(
                accountsDataAccess,
                studentsDataAccess,
                tutorsDataAccess,
                credentialHandler);

        IAccount newAccount = accountCreator.createAccount(
                        "fulopv@myumanitoba.ca",
                        "Nagyon jo")
                .setStudentProfile(
                        "Victoria",
                        "Education",
                        "She/Her")
                .setTutorProfile(
                        "Ms. Philip",
                        "Education",
                        "She/Her")
                .buildAccount();

        theTutorProfile = new TutorProfile(
                newAccount.getTutorProfile().get(),
                tutorsDataAccess);
    }

    @Test
    public void testHourlyRate() {
        theTutorProfile.setHourlyRate(420.69).updateUserProfile();
        ITutor updatedTutor = tutorsDataAccess
                .getTutorByEmail(theTutorProfile.getUserEmail())
                .get();
        assertEquals(
                420.69,
                updatedTutor.getHourlyRate(),
                0.01);
    }

    @Test
    public void testAvgReview() {
        theTutorProfile
                .addReview(5)
                .addReview(4)
                .addReview(5)
                .updateUserProfile();

        ITutor updatedTutor = tutorsDataAccess
                .getTutorByEmail(theTutorProfile.getUserEmail())
                .get();

        assertEquals(
                (double) (5 + 4 + 5) / 3,
                (double) updatedTutor.getReviewTotalSum() / (double) updatedTutor.getReviewCount(),
                0.1);
    }

    @Test
    public void testCourseList() {
        theTutorProfile
                .addCourse("COMP 1010", "Introduction")
                .addCourse("COMP 1010", "CS 1")
                .addCourse("ASTR 1830", "Life in Universe")
                .updateUserProfile();

        ITutor updatedTutor = tutorsDataAccess
                .getTutorByEmail(theTutorProfile.getUserEmail())
                .get();

        List<ICourse> updatedCourseList = updatedTutor.getCourses();

        assertNotNull(updatedCourseList);

        assertEquals(2, updatedCourseList.size());

        assertTrue(updatedCourseList.stream().anyMatch(
                course -> course.getCourseCode().equals("COMP 1010")
                        && course.getCourseName().equals("Introduction")));

        assertTrue(updatedCourseList.stream().anyMatch(
                course -> course.getCourseCode().equals("ASTR 1830")));
    }

    @Test
    public void testCourseListRemove() {
        theTutorProfile
                .addCourse("COMP 1010", "Introduction")
                .addCourse("COMP 1010", "CS 1")
                .addCourse("ASTR 1830", "Life in Universe")
                .updateUserProfile();

        theTutorProfile
                .removeCourse("COMP 1010")
                .removeCourse("COMP 1010")
                .updateUserProfile();

        ITutor updatedTutor = tutorsDataAccess
                .getTutorByEmail(theTutorProfile.getUserEmail())
                .get();

        List<ICourse> updatedCourseList = updatedTutor.getCourses();

        assertNotNull(updatedCourseList);

        assertEquals(1, updatedCourseList.size());

        assertFalse(updatedCourseList.stream().anyMatch(
                course -> course.getCourseCode().equals("COMP 1010")));

        assertTrue(updatedCourseList.stream().anyMatch(
                course -> course.getCourseCode().equals("ASTR 1830")));
    }

    @Test
    public void testPreferredLocation() {
        theTutorProfile
                .addPreferredLocation("420 Feltcher Argue")
                .addPreferredLocation("400 Feltcher Argue")
                .addPreferredLocation("400 Feltcher Argue")
                .updateUserProfile();

        ITutor updatedTutor = tutorsDataAccess
                .getTutorByEmail(theTutorProfile.getUserEmail())
                .get();

        List<String> preferredLocations =
                updatedTutor.getPreferredLocations();

        assertNotNull(preferredLocations);

        assertEquals(2, preferredLocations.size());

        assertTrue(preferredLocations.stream().anyMatch(
                location -> location.equals("420 Feltcher Argue")));

        assertTrue(preferredLocations.stream().anyMatch(
                location -> location.equals("400 Feltcher Argue")));
    }

    @Test
    public void testPreferredLocations() {
        theTutorProfile
                .addPreferredLocation("420 Feltcher Argue")
                .addPreferredLocation("400 Feltcher Argue")
                .addPreferredLocation("400 Feltcher Argue")
                .updateUserProfile();

        ArrayList<String> preferredLocations = new ArrayList<>();
        preferredLocations.add("420 Feltcher Argue");
        preferredLocations.add("102 Isbister");
        preferredLocations.add("400 Tier");

        theTutorProfile
                .addPreferredLocations(preferredLocations)
                .updateUserProfile();

        ITutor updatedTutor = tutorsDataAccess
                .getTutorByEmail(theTutorProfile.getUserEmail())
                .get();

        assertEquals(4, updatedTutor.getPreferredLocations().size());

        assertTrue(updatedTutor.getPreferredLocations().stream().anyMatch(
                location -> location.equals("400 Tier")));

        assertTrue(updatedTutor.getPreferredLocations().stream().anyMatch(
                location -> location.equals("420 Feltcher Argue")));
    }
}
