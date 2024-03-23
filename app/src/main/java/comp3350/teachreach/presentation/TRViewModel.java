package comp3350.teachreach.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TRViewModel extends ViewModel
{
    private final MutableLiveData<IAccount> account = new MutableLiveData<>();
    private final MutableLiveData<IStudent> student = new MutableLiveData<>();
    private final MutableLiveData<ITutor>   tutor   = new MutableLiveData<>();
    private final MutableLiveData<Boolean>  isTutor
                                                    =
            new MutableLiveData<Boolean>(
            false);

    private final MutableLiveData<List<ICourse>> courses
            = new MutableLiveData<>();

    private final MutableLiveData<List<IAccount>> accounts
            = new MutableLiveData<>();

    private final MutableLiveData<List<IStudent>> students
            = new MutableLiveData<>();

    private final MutableLiveData<List<ITutor>> tutors
            = new MutableLiveData<>();

    private final MutableLiveData<List<IAccount>> users
            = new MutableLiveData<>();

    private  MutableLiveData<List<IMessage>> sentMessages
            = new MutableLiveData<>();

    private  MutableLiveData<List<IMessage>> receivedMessages
            = new MutableLiveData<>();

    public MutableLiveData<List<IMessage>> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(MutableLiveData<List<IMessage>> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public MutableLiveData<List<IMessage>> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(MutableLiveData<List<IMessage>> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public LiveData<List<IAccount>> getUsers()
    {
        return users;
    }

    public void setUsers(List<IAccount> users)
    {
        this.users.setValue(users);
    }

    public LiveData<IAccount> getAccount()
    {
        return account;
    }

    public void setAccount(IAccount account)
    {
        this.account.setValue(account);
    }

    public LiveData<IStudent> getStudent()
    {
        return student;
    }

    public void setStudent(IStudent student)
    {
        this.student.setValue(student);
    }

    public LiveData<ITutor> getTutor()
    {
        return tutor;
    }

    public void setTutor(ITutor tutor)
    {
        this.tutor.setValue(tutor);
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

    public void setIsTutor()
    {
        this.isTutor.setValue(true);
    }

    public LiveData<Boolean> getIsTutor()
    {
        return isTutor;
    }
}
