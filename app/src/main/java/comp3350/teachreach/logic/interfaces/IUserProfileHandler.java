package comp3350.teachreach.logic.interfaces;

import comp3350.teachreach.objects.interfaces.IAccount;

public
interface IUserProfileHandler
{
    String getUserEmail();

    String getUserName();

    IUserProfileHandler setUserName(String name);

    String getUserPronouns();

    IUserProfileHandler setUserPronouns(String pronouns);

    String getUserMajor();

    IUserProfileHandler setUserMajor(String major);

    IAccount getUserAccount();

    void updateUserProfile();
}
