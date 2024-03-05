package comp3350.teachreach.objects;

import comp3350.teachreach.objects.interfaces.IUser;

public class User implements IUser {
    private String email;
    private String name;
    private String pronouns;
    private String major;

    public User(String email,
                String name, String pronouns, String major) {
        this.email = email;
        this.name = name;
        this.pronouns = pronouns;
        this.major = major;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IUser setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getPronouns() {
        return this.pronouns;
    }

    @Override
    public IUser setPronouns(String pronouns) {
        this.pronouns = pronouns;
        return this;
    }

    @Override
    public String getMajor() {
        return this.major;
    }

    @Override
    public IUser setMajor(String major) {
        this.major = major;
        return this;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public IUser setEmail(String email) {
        this.email = email;
        return this;
    }
}
