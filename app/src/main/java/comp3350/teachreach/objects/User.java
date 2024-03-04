package comp3350.teachreach.objects;

public abstract class User {
    private String name;
    private String pronouns;
    private String major;

    public User(String name, String pronouns, String major) {
        this.name = name;
        this.pronouns = pronouns;
        this.major = major;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPronouns() {
        return this.pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

}
