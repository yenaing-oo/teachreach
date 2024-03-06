package comp3350.teachreach.objects;

import comp3350.teachreach.objects.interfaces.IAccount;

public
class Account implements IAccount
{
    private int    accountID;
    private int    tutorID;
    private String accountEmail;
    private String accountPassword;

    public
    Account(String accountEmail, String accountPassword)
    {
        this.accountEmail    = accountEmail;
        this.accountPassword = accountPassword;
        this.accountID       = -1;
        this.tutorID         = -1;
    }

    public
    Account(String accountEmail, String accountPassword, int accountID)
    {
        this.accountEmail    = accountEmail;
        this.accountPassword = accountPassword;
        this.accountID       = accountID;
        this.tutorID         = -1;
    }

    @Override
    public
    String getAccountEmail()
    {
        return this.accountEmail;
    }

    @Override
    public
    void setAccountEmail(String accountEmail)
    {
        this.accountEmail = accountEmail;
    }

    @Override
    public
    String getAccountPassword()
    {
        return this.accountPassword;
    }

    @Override
    public
    void setAccountPassword(String accountPassword)
    {
        this.accountPassword = accountPassword;
    }

    @Override
    public
    boolean isTutor()
    {
        return this.tutorID != -1;
    }

    @Override
    public
    int getAccountID()
    {
        return this.accountID;
    }

    @Override
    public
    void setAccountID(int accountID)
    {
        this.accountID = accountID;
    }

    @Override
    public
    int getTutorID()
    {
        return tutorID;
    }

    @Override
    public
    void setTutorID(int tutorID)
    {
        this.tutorID = tutorID;
    }
}
