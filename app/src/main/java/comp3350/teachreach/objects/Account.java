package comp3350.teachreach.objects;

import java.util.Optional;

public class Account {
    private String email;
    private String password;
    private Tutor tutorProfile = new NullTutor();
    private Student studentProfile = null;

    private Account(String email, String password) {
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

    private void setStudentProfile(Student profile) {
        this.studentProfile = profile;
    }

    private void setTutorProfile(Tutor profile) {
        this.tutorProfile = profile;
    }

    public Optional<Student> getStudentProfile() {
        return Optional.ofNullable(this.studentProfile);
    }

    public Tutor getTutorProfile() {
        return this.tutorProfile;
    }

    public static class Builder {
        private Account account;

        public Builder(String email, String password) {
            account = new Account(email, password);
        }

        public Builder(Account ac) {
            this.account = ac;
        }

        public Builder studentProfile(Student studentProfile) {
            account.setStudentProfile(studentProfile);
            return this;
        }

        public Builder tutorProfile(Tutor tutorProfile) {
            account.setTutorProfile(tutorProfile);
            return this;
        }

        public Optional<Account> build() {
            return this.account.getStudentProfile().isPresent() ?
                    Optional.of(this.account) : Optional.empty();
        }
    }
}
