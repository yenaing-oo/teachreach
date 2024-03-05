package comp3350.teachreach.objects;

import java.util.Optional;

public class Account implements IAccount {

    private int accountID;
    private String email;
    private String password;
    private ITutor tutorProfile = new NullTutor();
    private IStudent studentProfile = null;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        this.accountID = -1;
    }
    public Account(String email, String password, int accountID) {
        this.email = email;
        this.password = password;
        this.accountID = accountID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isTutor() {
        return !(this.tutorProfile instanceof NullTutor);
    }

    public Optional<IStudent> getStudentProfile() {
        return Optional.ofNullable(this.studentProfile);
    }

    public IAccount setStudentProfile(IStudent profile) {
        this.studentProfile = profile;
        return studentProfile.setOwner(this);
    }

    public Optional<ITutor> getTutorProfile() {
        return Optional.ofNullable(this.tutorProfile);
    }

    public IAccount setTutorProfile(ITutor profile) {
        this.tutorProfile = profile;
        return tutorProfile.setOwner(this);
    }

    public int getAccountID() {
        return this.accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
