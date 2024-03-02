package comp3350.teachreach.logic.profile;

import comp3350.teachreach.objects.IAccount;

public interface IUserProfile {
    String getUserEmail();
    String getUserName();
    String getUserPronouns();
    String getUserMajor();
    IAccount getUserAccount();

    IUserProfile setUserName(String name);
    IUserProfile setUserPronouns(String pronouns);
    IUserProfile setUserMajor(String major);

    void updateUserProfile();
}
