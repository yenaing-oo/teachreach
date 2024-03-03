package comp3350.teachreach.objects;

public interface ISession {

    ISession acceptSession();
    IStudent getStudent();
    ISession setStudent(IStudent newStudent);
    ITutor getTutor();
    ISession setTutor(ITutor newTutor);
    ISession setSessionID(int id);
    int getSessionID();
    boolean getStage();
    ISession setStage(boolean decision);
    TimeSlice getTime();
    ISession setTime(TimeSlice time);
    String getLocation();
    ISession setLocation(String location);
}
