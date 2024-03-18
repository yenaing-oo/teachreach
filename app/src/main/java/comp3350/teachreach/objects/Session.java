package comp3350.teachreach.objects;

import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.ITimeSlice;

public
class Session implements ISession {
    private int sessionID;
    private int studentID;
    private int tutorID;
    private ITimeSlice timeRange;
    private boolean approved;
    private String atLocation;

    public Session(int sessionID,
                   int studentID,
                   int tutorID,
                   ITimeSlice timeRange,
                   String atLocation) {
        this.sessionID = sessionID;
        this.studentID = studentID;
        this.tutorID = tutorID;
        this.timeRange = timeRange;
        this.atLocation = atLocation;
        this.approved = false;
    }

    public Session(int sessionID,
                   int studentID,
                   int tutorID,
                   TimeSlice timeRange,
                   String atLocation,
                   boolean approved) {
        this.sessionID = sessionID;
        this.studentID = studentID;
        this.tutorID = tutorID;
        this.timeRange = timeRange;
        this.approved = false;
        this.atLocation = atLocation;
        this.approved = approved;
    }

    @Override
    public int getSessionStudentID() {
        return this.studentID;
    }

    @Override
    public Session setSessionStudentID(int studentID) {
        this.studentID = studentID;
        return this;
    }

    @Override
    public int getSessionTutorID() {
        return tutorID;
    }

    @Override
    public void setSessionTutorID(int tutorID) {
        this.tutorID = tutorID;
    }

    @Override
    public int getSessionID() {
        return this.sessionID;
    }

    @Override
    public Session setSessionID(int sessionID) {
        this.sessionID = sessionID;
        return this;
    }

    @Override
    public ITimeSlice getTime() {
        return this.timeRange;
    }

    @Override
    public Session setTime(TimeSlice time) {
        timeRange = time;
        return this;
    }

    @Override
    public String getSessionLocation() {
        return this.atLocation;
    }

    @Override
    public Session setSessionLocation(String location) {
        this.atLocation = location;
        return this;
    }

    @Override
    public boolean getAcceptedStatus() {
        return this.approved;
    }

    @Override
    public Session approve() {
        this.approved = true;
        return this;
    }
}
