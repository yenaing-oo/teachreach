package comp3350.teachreach.objects;

import comp3350.teachreach.objects.interfaces.ISession;

public
class Session implements ISession
{
    private int       sessionID;
    private int       studentID;
    private int       tutorID;
    private TimeSlice atTime;
    private boolean   approvedByTutor = false;
    private String    atLocation;

    public
    Session(int sessionID,
            int studentID,
            int tutorID,
            int year,
            int month,
            int day,
            int hour,
            boolean halfPastHour,
            String atLocation)
    {
        this.sessionID = sessionID;
        this.studentID = studentID;
        this.tutorID   = tutorID;
        this.atTime    = TimeSlice.ofHalfAnHourFrom(year,
                                                    month,
                                                    day,
                                                    hour,
                                                    halfPastHour ? 30 : 0);

        this.atLocation = atLocation;
    }

    public
    Session(int sessionID,
            int studentID,
            int tutorID,
            TimeSlice sessionTime,
            String atLocation)
    {
        this.sessionID       = sessionID;
        this.studentID       = studentID;
        this.tutorID         = tutorID;
        this.atTime          = sessionTime;
        this.approvedByTutor = false;
        this.atLocation      = atLocation;
    }

    @Override
    public
    int getSessionStudentID()
    {
        return this.studentID;
    }

    @Override
    public
    Session setSessionStudentID(int studentID)
    {
        this.studentID = studentID;
        return this;
    }

    @Override
    public
    int getSessionTutorID()
    {
        return tutorID;
    }

    @Override
    public
    void setSessionTutorID(int tutorID)
    {
        this.tutorID = tutorID;
    }

    @Override
    public
    int getSessionID()
    {
        return this.sessionID;
    }

    @Override
    public
    Session setSessionID(int sessionID)
    {
        this.sessionID = sessionID;
        return this;
    }

    @Override
    public
    TimeSlice getTime()
    {
        return this.atTime;
    }

    @Override
    public
    Session setTime(TimeSlice time)
    {
        atTime = time;
        return this;
    }

    @Override
    public
    String getSessionLocation()
    {
        return this.atLocation;
    }

    @Override
    public
    Session setSessionLocation(String location)
    {
        this.atLocation = location;
        return this;
    }

    @Override
    public
    boolean getAcceptedStatus()
    {
        return this.approvedByTutor;
    }

    @Override
    public
    Session approvedByTutor()
    {
        this.approvedByTutor = true;
        return this;
    }
}
