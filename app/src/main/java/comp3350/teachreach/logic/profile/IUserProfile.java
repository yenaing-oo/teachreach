package comp3350.teachreach.logic.profile;

import comp3350.teachreach.objects.IAccount;

public interface IUserProfile {
    String getUserEmail();

    String getUserName();

    IUserProfile setUserName(String name);

    String getUserPronouns();

    IUserProfile setUserPronouns(String pronouns);

    String getUserMajor();

    IUserProfile setUserMajor(String major);

    IAccount getUserAccount();

    void updateUserProfile();
}
