package comp3350.teachreach.objects.interfaces;

import java.util.Optional;

public
interface IAccount
{

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    boolean isTutor();

    Optional<IStudent> getStudentProfile();

    IAccount setStudentProfile(IStudent profile);

    Optional<ITutor> getTutorProfile();

    IAccount setTutorProfile(ITutor profile);

    int getAccountID();

    void setAccountID(int accountID);

}