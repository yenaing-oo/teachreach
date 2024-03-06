package comp3350.teachreach.logic.profile;

import java.util.NoSuchElementException;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudent;
import comp3350.teachreach.logic.interfaces.IUserProfile;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;

public
class StudentProfileFetcher implements IUserProfile
{
    private final IStudent       theStudent;
    private final AccessStudent  accessStudent;
    private final AccessAccounts accessAccounts;

    public
    StudentProfileFetcher(IStudent theStudent)
    {

        this.theStudent     = theStudent;
        this.accessStudent  = new AccessStudent();
        this.accessAccounts = new AccessAccounts();
    }

    public
    StudentProfileFetcher(int studentAccountID)
    {
        this.accessStudent  = new AccessStudent();
        this.accessAccounts = new AccessAccounts();
        this.theStudent     = accessStudent.getStudentByAccountID(
                studentAccountID)
    }

    @Override
    public
    String getUserEmail()
    {
        return this.theStudent.getEmail();
    }

    @Override
    public
    String getUserName()
    {
        return this.theStudent.getUserName();
    }

    @Override
    public
    IUserProfile setUserName(String name)
    {
        this.theStudent.setName(name);
        return this;
    }

    @Override
    public
    String getUserPronouns()
    {
        return this.theStudent.getUserPronouns();
    }

    @Override
    public
    IUserProfile setUserPronouns(String pronouns)
    {
        this.theStudent.setPronouns(pronouns);
        return this;
    }

    @Override
    public
    String getUserMajor()
    {
        return this.theStudent.getUserMajor();
    }

    @Override
    public
    IUserProfile setUserMajor(String major)
    {
        this.theStudent.setMajor(major);
        return this;
    }

    @Override
    public
    IAccount getUserAccount()
    {
        return this.accessAccounts
                .getAccounts()
                .stream()
                .filter(a -> a.getAccountEmail().equals(theStudent.getEmail()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public
    void updateUserProfile()
    {
        this.accessStudent.updateStudent(theStudent);
    }
}
