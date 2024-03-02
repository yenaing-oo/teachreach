package comp3350.teachreach.objects;

public interface IUser {
    String getName();

    void setName(String name);

    String getPronouns();

    void setPronouns(String pronouns);

    String getMajor();

    void setMajor(String major);

    IAccount getOwner();
}
