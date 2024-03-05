package comp3350.teachreach.objects;

import java.util.Optional;

import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class Account implements IAccount {
    private String email;
    private String password;
    private ITutor tutorProfile = new NullTutor();
    private IStudent studentProfile = null;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
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
        return this;
    }

    public Optional<ITutor> getTutorProfile() {
        return Optional.ofNullable(this.tutorProfile);
    }

    public IAccount setTutorProfile(ITutor profile) {
        this.tutorProfile = profile;
        return this;
    }

}
