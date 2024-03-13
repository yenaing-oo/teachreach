package comp3350.teachreach.logic.profile;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;

public
class StudentProfileHandler implements IUserProfileHandler
{
    private final AccessStudents accessStudents;
    private final AccessAccounts accessAccounts;
    private final IStudent       theStudent;
    private final IAccount       parentAccount;

    public
    StudentProfileHandler(IStudent theStudent)
    {
        this.accessStudents = new AccessStudents();
        this.accessAccounts = new AccessAccounts();
        this.theStudent = theStudent;
        this.parentAccount = accessAccounts
                .getAccounts()
                .get(theStudent.getAccountID());
    }

    public StudentProfileHandler(int studentID) {
        this.accessStudents = new AccessStudents();
        this.accessAccounts = new AccessAccounts();
        this.theStudent = accessStudents.getStudentByStudentID(studentID);
        this.parentAccount = accessAccounts
                .getAccounts()
                .get(theStudent.getAccountID());
    }

    @Override
    public
    String getUserEmail()
    {
        return this.parentAccount.getAccountEmail();
    }

    @Override
    public
    String getUserName()
    {
        return this.parentAccount.getUserName();
    }

    @Override
    public
    String getUserPronouns()
    {
        return this.parentAccount.getUserPronouns();
    }

    @Override
    public
    String getUserMajor()
    {
        return this.parentAccount.getUserMajor();
    }

    @Override
    public
    IAccount getUserAccount()
    {
        return parentAccount;
    }
}
