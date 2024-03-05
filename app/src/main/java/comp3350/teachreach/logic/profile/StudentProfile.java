package comp3350.teachreach.logic.profile;

import java.util.NoSuchElementException;

import comp3350.teachreach.logic.DAOs.AccessAccount;
import comp3350.teachreach.logic.DAOs.AccessStudent;
import comp3350.teachreach.logic.interfaces.IUserProfile;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;

public class StudentProfile implements IUserProfile {
    private IStudent theStudent;
    private AccessStudent accessStudent;
    private AccessAccount accessAccount;


    public StudentProfile(
            IStudent theStudent) {

        this.theStudent = theStudent;
        this.accessStudent = new AccessStudent();
        this.accessAccount = new AccessAccount();
    }

    @Override
    public String getUserEmail() {
        return this.theStudent.getEmail();
    }

    @Override
    public String getUserName() {
        return this.theStudent.getName();
    }

    @Override
    public String getUserPronouns() {
        return this.theStudent.getPronouns();
    }

    @Override
    public String getUserMajor() {
        return this.theStudent.getMajor();
    }

    @Override
    public IAccount getUserAccount() {
        return this.accessAccount
                .getAccounts()
                .stream()
                .filter(a -> a.getEmail().equals(theStudent.getEmail()))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public IUserProfile setUserName(String name) {
        this.theStudent.setName(name);
        return this;
    }

    @Override
    public IUserProfile setUserPronouns(String pronouns) {
        this.theStudent.setPronouns(pronouns);
        return this;
    }

    @Override
    public IUserProfile setUserMajor(String major) {
        this.theStudent.setMajor(major);
        return this;
    }

    @Override
    public void updateUserProfile() {
        this.accessStudent.updateStudent(theStudent);
    }
}
