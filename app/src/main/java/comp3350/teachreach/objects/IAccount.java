package comp3350.teachreach.objects;

import java.util.Optional;

public interface IAccount {

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);
    boolean isTutor();

    IAccount setStudentProfile(IStudent profile);

    IAccount setTutorProfile(ITutor profile);

    Optional<IStudent> getStudentProfile();

    Optional<ITutor> getTutorProfile();

    int getAccountID();

    void setAccountID(int accountID);

}