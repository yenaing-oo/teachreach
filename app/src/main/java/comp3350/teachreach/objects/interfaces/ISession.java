package comp3350.teachreach.objects.interfaces;

import comp3350.teachreach.objects.TimeSlice;

public
interface ISession
{
    int getSessionStudentID();

    ISession setSessionStudentID(int studentID);

    int getSessionTutorID();

    void setSessionTutorID(int tutorID);

    int getSessionID();

    ISession setSessionID(int sessionID);

    ITimeSlice getTimeRange();

    ISession setTime(TimeSlice time);

    String getSessionLocation();

    ISession setSessionLocation(String location);

    int getStatus();

    ISession approve();

    ISession reject();

    ISession pend();
}
