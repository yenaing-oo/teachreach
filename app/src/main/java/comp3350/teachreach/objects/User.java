package comp3350.teachreach.objects;

public class User implements IUser {
    private String name;
    private String pronouns;
    private String major;
    private IAccount belongsTo;

    public User(String name,
                String pronouns,
                String major,
                IAccount parentAccount) {
        this.name = name;
        this.pronouns = pronouns;
        this.major = major;
        this.belongsTo = parentAccount;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPronouns() {
        return this.pronouns;
    }

    @Override
    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    @Override
    public String getMajor() {
        return this.major;
    }

    @Override
    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public IAccount getOwner() {
        return this.belongsTo;
    }
}
