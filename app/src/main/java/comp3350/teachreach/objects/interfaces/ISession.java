package comp3350.teachreach.objects.interfaces;

import comp3350.teachreach.objects.TimeSlice;

public
interface ISession
{

    ISession acceptSession();

    IStudent getStudent();

    ISession setStudent(IStudent newStudent);

    ITutor getTutor();

    ISession setTutor(ITutor newTutor);

    int getSessionID();

    ISession setSessionID(int id);

    boolean getStage();

    ISession setStage(boolean decision);

    TimeSlice getTime();

    ISession setTime(TimeSlice time);

    String getLocation();

    ISession setLocation(String location);
}
