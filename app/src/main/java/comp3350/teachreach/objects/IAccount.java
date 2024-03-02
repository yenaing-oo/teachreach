package comp3350.teachreach.objects;

public interface IAccount {

    public String getEmail();

    public void setEmail(String email);

    public String getPassword();

    public void setPassword(String password);

    public Account setStudentProfile(IStudent profile);

    public Account setTutorProfile(ITutor profile);

    public IStudent getStudentProfile();

    public ITutor getTutorProfile();

}