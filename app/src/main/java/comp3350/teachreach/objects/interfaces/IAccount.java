package comp3350.teachreach.objects.interfaces;

import java.util.Optional;

public
interface IAccount
{

    String getAccountEmail();

    void setAccountEmail(String accountEmail);

    String getAccountPassword();

    void setAccountPassword(String accountPassword);

    boolean isTutor();

    int getAccountID();

    void setAccountID(int accountID);

    int getTutorID();

    void setTutorID(int tutorID);
}