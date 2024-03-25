package comp3350.teachreach.presentation.profile.tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import comp3350.teachreach.logic.exceptions.input.InvalidInputException;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorProfileViewModel extends ViewModel
{
    private final MutableLiveData<List<String>> tutoredCoursesCode
            = new MutableLiveData<>();

    private final MutableLiveData<List<String>> preferredLocations
            = new MutableLiveData<>();

    private final MutableLiveData<ITutor>   tutor        =
            new MutableLiveData<>();
    private final MutableLiveData<IAccount> tutorAccount
                                                         =
            new MutableLiveData<>();

    public LiveData<List<String>> getTutoredCoursesCode()
    {
        return this.tutoredCoursesCode;
    }

    public void setTutoredCoursesCode(List<String> tutoredCoursesCode)
    {
        this.tutoredCoursesCode.setValue(tutoredCoursesCode);
    }

    public void addCourse(ITutor t, String courseCode, String courseName)
            throws InvalidInputException
    {
        ITutorProfileHandler tph = new TutorProfileHandler(t);
        tph.addCourse(courseCode, courseName);
        this.tutoredCoursesCode.postValue(tph.getCourseCodeList());
    }

    public void addLocation(ITutor t, String l) throws InvalidInputException
    {
        ITutorProfileHandler tph = new TutorProfileHandler(t);
        tph.addPreferredLocation(l);
        this.preferredLocations.postValue(tph.getPreferredLocations());
    }

    public LiveData<List<String>> getPreferredLocations()
    {
        return this.preferredLocations;
    }

    public void setPreferredLocations(List<String> l)
    {
        this.preferredLocations.setValue(l);
    }

    public void postTutor(ITutor t)
    {
        this.tutor.postValue(t);
    }

    public LiveData<ITutor> getTutor()
    {
        return tutor;
    }

    public void setTutor(ITutor t)
    {
        this.tutor.setValue(t);
    }

    public void postTutorAccount(IAccount a)
    {
        this.tutorAccount.postValue(a);
    }

    public LiveData<IAccount> getTutorAccount()
    {
        return tutorAccount;
    }

    public void setTutorAccount(IAccount a)
    {
        this.tutorAccount.setValue(a);
    }
}
