package comp3350.teachreach.logic.profile;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorProfileHandler
        implements ITutorProfileHandler, IUserProfileHandler
{
    private final AccessAccounts accessAccounts;
    private final AccessTutors   accessTutors;
    private       ITutor         theTutor;
    private       IAccount       parentAccount;

    public TutorProfileHandler(ITutor theTutor)
    {
        accessAccounts     = new AccessAccounts();
        accessTutors       = new AccessTutors();
        this.theTutor      = theTutor;
        this.parentAccount = accessAccounts
                .getAccounts()
                .get(theTutor.getAccountID());
    }

    public TutorProfileHandler(int tutorID)
    {
        accessAccounts     = new AccessAccounts();
        accessTutors       = new AccessTutors();
        this.theTutor      = accessTutors.getTutorByTutorID(tutorID);
        this.parentAccount = accessAccounts
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
        return new ArrayList<>();
    }

    @Override
    public List<String> getPreferredLocations()
    {
        return new ArrayList<>();
    }

    @Override
    public ITutorProfileHandler addReview(int score)
    {
        return this;
    }

    @Override
    public ITutorProfileHandler addCourse(String courseCode, String courseName)
    {
        return this;
    }

    @Override
    public ITutorProfileHandler removeCourse(String courseCode)
    {
        return this;
    }

    @Override
    public ITutorProfileHandler addPreferredLocation(String preferredLocation)
    {
        return this;
    }

    @Override
    public ITutorProfileHandler addPreferredLocations(List<String> preferredLocations)
    {
        preferredLocations.forEach(this::addPreferredLocation);
        return this;
    }

    @Override
    public ITutor updateTutorProfile()
    {
        theTutor = accessTutors.updateTutor(theTutor);
        return theTutor;
    }
}
