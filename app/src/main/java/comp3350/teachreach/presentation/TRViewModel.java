package comp3350.teachreach.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TRViewModel extends ViewModel
{
    private final MutableLiveData<Integer> accountId = new MutableLiveData<>();
    private final MutableLiveData<Integer> studentId = new MutableLiveData<>();
    private final MutableLiveData<Integer> tutorId   = new MutableLiveData<>();

    private final MutableLiveData<List<ICourse>> courses
            = new MutableLiveData<>();

    private final MutableLiveData<List<IAccount>> accounts
            = new MutableLiveData<>();

    private final MutableLiveData<List<IStudent>> students
            = new MutableLiveData<>();

    private final MutableLiveData<List<ITutor>> tutors
            = new MutableLiveData<>();

    public LiveData<Integer> getAccountId()
    {
        return accountId;
    }

    public void setAccountId(int accountId)
    {
        this.accountId.setValue(accountId);
    }

    public LiveData<Integer> getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int id)
    {
        this.studentId.setValue(id);
    }

    public LiveData<Integer> getTutorId()
    {
        return tutorId;
    }

    public void setTutorId(int id)
    {
        this.tutorId.setValue(id);
    }

    public LiveData<List<ICourse>> getCourses()
    {
        return courses;
    }

    public void setCourses(List<ICourse> courses)
    {
        this.courses.setValue(courses);
    }

    public LiveData<List<IAccount>> getAccounts()
    {
        return accounts;
    }

    public void setAccounts(List<IAccount> accounts)
    {
        this.accounts.setValue(accounts);
    }

    public LiveData<List<IStudent>> getStudents()
    {
        return students;
    }

    public void setStudents(List<IStudent> students)
    {
        this.students.setValue(students);
    }

    public LiveData<List<ITutor>> getTutors()
    {
        return tutors;
    }

    public void setTutors(List<ITutor> tutors)
    {
        this.tutors.setValue(tutors);
    }
}
