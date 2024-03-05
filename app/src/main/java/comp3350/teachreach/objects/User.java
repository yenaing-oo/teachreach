package comp3350.teachreach.objects;

public class User implements IUser {
    private String name;
    private String pronouns;
    private String major;

    public User(String name,
                String pronouns,
                String major) {
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

}
