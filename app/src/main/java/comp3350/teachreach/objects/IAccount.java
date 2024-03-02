package comp3350.teachreach.objects;

import java.util.Optional;

public interface IAccount {

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    Account setStudentProfile(IStudent profile);

    Account setTutorProfile(ITutor profile);

    Optional<IStudent> getStudentProfile();

    Optional<ITutor> getTutorProfile();

}