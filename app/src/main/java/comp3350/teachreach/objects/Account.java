package comp3350.teachreach.objects;

import java.util.Optional;

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

    public Account setStudentProfile(IStudent profile) {
        this.studentProfile = profile;
        return this;
    }

    public Account setTutorProfile(ITutor profile) {
        this.tutorProfile = profile;
        return this;
    }

    public Optional<IStudent> getStudentProfile() {
        return Optional.ofNullable(this.studentProfile);
    }

    public Optional<ITutor> getTutorProfile() {
        return Optional.ofNullable(this.tutorProfile);
    }

}
