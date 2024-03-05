package comp3350.teachreach.objects;

import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class Session implements ISession {
    private int sessionID;
    private IStudent student;
    private ITutor tutor;
    private TimeSlice atTime;
    private boolean accepted;
    private String location;

    public Session(
            IStudent student, ITutor tutor,
            int day, int month, int year,
            int hour, int minute,
            int durationInMinutes, String location) {
        this.student = student;
        this.tutor = tutor;
        atTime = TimeSlice.of(
                year, month, day, hour, minute,
                durationInMinutes);
        this.accepted = false;
        this.location = location;
        this.sessionID = -1;
    }

    public Session(
            IStudent theStudent, ITutor theTutor,
            TimeSlice sessionTime, String location) {
        this.student = theStudent;
        this.tutor = theTutor;
        atTime = sessionTime;
        this.accepted = false;
        this.location = location;
        this.sessionID = -1;
    }

    public ISession acceptSession() {
        this.accepted = true;
        return this;
    }

    public IStudent getStudent() {
        return this.student;
    }

    @Override
    public ISession setStudent(IStudent newStudent) {
        this.student = newStudent;
        return this;
    }

    public ITutor getTutor() {
        return this.tutor;
    }

    @Override
    public ISession setTutor(ITutor newTutor) {
        this.tutor = newTutor;
        return this;
    }

    @Override
    public ISession setSessionID(int id) {
        this.sessionID = id;
        return this;
    }

    @Override
    public int getSessionID() {
        return this.sessionID;
    }

    public TimeSlice getTime() {
        return this.atTime;
    }

    @Override
    public ISession setTime(TimeSlice time) {
        atTime = time;
        return this;
    }

    public boolean getStage() {
        return this.accepted;
    }

    public ISession setStage(boolean decision) {
        this.accepted = decision;
        return this;
    }

    public String getLocation() {
        return this.location;
    }

    public ISession setLocation(String location) {
        this.location = location;
        return this;
    }
}
