package comp3350.teachreach.logic.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.AccessTutorLocation;
import comp3350.teachreach.logic.DAOs.AccessTutoredCourses;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.account.InputValidator;
import comp3350.teachreach.logic.exceptions.DataAccessException;
import comp3350.teachreach.logic.exceptions.input.InvalidInputException;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorProfileHandler
        implements ITutorProfileHandler, IUserProfileHandler
{
    private final AccessAccounts       accessAccounts;
    private final AccessTutors         accessTutors;
    private final AccessTutoredCourses accessTutoredCourses;
    private final AccessTutorLocation  accessTutorLocation;
    private final AccessCourses        accessCourses;
    private       ITutor               theTutor;
    private       IAccount             parentAccount;

    public TutorProfileHandler(ITutor theTutor)
    {
        accessAccounts       = new AccessAccounts();
        accessTutors         = new AccessTutors();
        accessTutoredCourses = new AccessTutoredCourses();
        accessCourses        = new AccessCourses();
        accessTutorLocation  = new AccessTutorLocation();
        this.theTutor        = theTutor;
        this.parentAccount   = accessAccounts
                .getAccounts()
                .get(theTutor.getAccountID());
    }

    public TutorProfileHandler(int tutorID)
    {
        accessAccounts       = new AccessAccounts();
        accessTutors         = new AccessTutors();
        accessTutoredCourses = new AccessTutoredCourses();
        accessTutorLocation  = new AccessTutorLocation();
        accessCourses        = new AccessCourses();
        this.theTutor        = accessTutors.getTutorByTutorID(tutorID);
        this.parentAccount   = accessAccounts
                .getAccounts()
                .get(theTutor.getAccountID());
    }

    @Override
    public String getUserEmail()
    {
        return this.parentAccount.getAccountEmail();
    }

    @Override
    public String getUserName()
    {
        return this.parentAccount.getUserName();
    }

    @Override
    public String getUserPronouns()
    {
        return this.parentAccount.getUserPronouns();
    }

    @Override
    public String getUserMajor()
    {
        return this.parentAccount.getUserMajor();
    }

    @Override
    public IAccount getUserAccount()
    {
        return parentAccount;
    }

    @Override
    public double getHourlyRate()
    {
        return theTutor.getHourlyRate();
    }

    @Override
    public ITutorProfileHandler setHourlyRate(double hourlyRate)
    {
        this.theTutor.setHourlyRate(hourlyRate);
        return this;
    }

    @Override
    public double getAvgReview()
    {
        return theTutor.getReviewCount() > 0 ?
               ((double) theTutor.getReviewSum() /
                (double) theTutor.getReviewCount()) :
               0;
    }

    @Override
    public int getReviewCount()
    {
        return this.theTutor.getReviewCount();
    }

    @Override
    public int getReviewSum()
    {
        return this.theTutor.getReviewSum();
    }

    @Override
    public List<ICourse> getCourses()
    {
        return accessTutoredCourses.getTutoredCoursesByTutorID(theTutor.getTutorID());
    }

    @Override
    public List<String> getCourseCodeList()
    {
        return accessTutoredCourses
                .getTutoredCoursesByTutorID(theTutor.getTutorID())
                .stream()
                .map(ICourse::getCourseCode)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getCourseDescriptionList()
    {
        return accessTutoredCourses
                .getTutoredCoursesByTutorID(theTutor.getTutorID())
                .stream()
                .map(ICourse::getCourseName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPreferredLocations()
    {
        return accessTutorLocation.getTutorLocationByTutorID(theTutor.getTutorID());
    }

    @Override
    public ITutorProfileHandler addReview(int score)
    {
        return this;
    }

    @Override
    public ITutorProfileHandler addCourse(String courseCode, String courseName)
            throws InvalidInputException
    {
        InputValidator.validateInput(courseCode);
        InputValidator.validateInput(courseName);
        try {
            accessCourses.insertCourse(new Course(courseCode, courseName));
        } catch (final DataAccessException ignored) {
        } finally {
            accessTutoredCourses.storeTutorCourse(theTutor.getTutorID(),
                                                  courseCode);
        }
        return this;
    }

    @Override
    public ITutorProfileHandler removeCourse(String courseCode)
    {
        return this;
    }

    @Override
    public ITutorProfileHandler addPreferredLocation(String preferredLocation)
            throws InvalidInputException
    {
        InputValidator.validateInput(preferredLocation);

        accessTutorLocation.storeTutorLocation(theTutor.getTutorID(),
                                               preferredLocation);

        return this;
    }

    @Override
    public ITutor updateTutorProfile()
    {
        theTutor = accessTutors.updateTutor(theTutor);
        return theTutor;
    }
}
