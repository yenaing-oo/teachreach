package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.ISessionPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.objects.ISession;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.TimeSlice;

public class SessionStub implements ISessionPersistence {

    List<ISession> sessions;
    int sessionIDCounter;
    IAccountPersistence accountsDataAccess;
    IStudentPersistence studentsDataAccess;
    ITutorPersistence tutorsDataAccess;

    public SessionStub() {
        sessions = new ArrayList<>();
        sessionIDCounter = 0;

        accountsDataAccess = new AccountStub();
        studentsDataAccess = new StudentStub(accountsDataAccess);
        tutorsDataAccess = new TutorStub(accountsDataAccess);
    }

    @Override
    public ISession storeSession(
            IStudent theStudent, ITutor theTutor,
            TimeSlice sessionTime, String location) {
        ISession newSession = new Session(
                theStudent, theTutor, sessionTime, location);
        return newSession.setSessionID(sessionIDCounter++);
    }

    @Override
    public boolean deleteSession(ISession session) {
        return sessions.removeIf(otherSession ->
                session.getSessionID() == otherSession.getSessionID());
    }

    @Override
    public boolean updateSession(ISession session) {
        ISession updatedSession = null;
        boolean result = false;
        for (ISession s : sessions) {
            if (s.getSessionID() == session.getSessionID()) {
                s.setLocation(session.getLocation());
                s.setStudent(session.getStudent());
                s.setTutor(session.getTutor());
                s.setStage(session.getStage());
                updatedSession = s;
                result = true;
                break;
            }
        }
        if (updatedSession == null) {
            throw new NoSuchElementException();
        }
        return result;
    }

    @Override
    public List<ISession> getSessionsByRangeForStudent(
            String studentEmail, TimeSlice range) {
        return sessions
                .stream()
                .filter(session ->
                        range.canContain(session.getTime()) &&
                                session.getStudent()
                                        .getOwner()
                                        .getEmail()
                                        .equals(studentEmail))
                .collect(Collectors.toList());
    }

    @Override
    public List<ISession> getSessionsByRangeForTutor(
            String studentEmail, TimeSlice range) {
        return sessions
                .stream()
                .filter(session ->
                        range.canContain(session.getTime()) &&
                                session.getTutor()
                                        .getOwner()
                                        .getEmail()
                                        .equals(studentEmail))
                .collect(Collectors.toList());
    }

    @Override
    public List<ISession> getPendingSessionRequests(String tutorEmail) {
        return sessions
                .stream()
                .filter(session ->
                        session.getTutor()
                                .getOwner()
                                .getEmail()
                                .equals(tutorEmail) &&
                                session.getStage())
                .collect(Collectors.toList());
    }
}
