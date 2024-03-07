package comp3350.teachreach.logic.interfaces;

import comp3350.teachreach.objects.interfaces.IAccount;

public
interface IUserProfileHandler
{
    String getUserEmail();

    String getUserName();

    String getUserPronouns();

    String getUserMajor();

    IAccount getUserAccount();
}
