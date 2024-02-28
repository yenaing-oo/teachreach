package comp3350.teachreach.objects;

public class Account {
    private String email;
    private String password;
    private Tutor tutorProfile;
    private Student studentProfile;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
