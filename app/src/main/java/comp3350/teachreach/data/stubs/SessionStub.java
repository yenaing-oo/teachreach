package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class SessionStub implements ISessionPersistence
{

    List<ISession>      sessions;
    int                 sessionIDCounter;
    IAccountPersistence accountsDataAccess;
    IStudentPersistence studentsDataAccess;
    ITutorPersistence   tutorsDataAccess;

    public
    SessionStub()
    {
        sessions         = new ArrayList<>();
        sessionIDCounter = 0;

        accountsDataAccess = new AccountStub();
        studentsDataAccess = new StudentStub();
        tutorsDataAccess   = new TutorStub(accountsDataAccess);
    }

    @Override
    public
    ISession storeSession(IStudent theStudent,
                          ITutor theTutor,
                          TimeSlice sessionTime,
                          String location)
    {
        ISession newSession = new Session(theStudent,
                                          theTutor,
                                          sessionTime,
                                          location);
        return newSession.setSessionID(sessionIDCounter++);
    }

    @Override
    public
    boolean deleteSession(ISession session)
    {
        return sessions.removeIf(otherSession -> session.getSessionID() ==
                                                 otherSession.getSessionID());
    }

    /**
     * @param studentID
     * @param tutorID
     * @param sessionTime
     * @param location
     * @return
     */
    @Override
    public
    ISession storeSession(int studentID,
                          int tutorID,
                          TimeSlice sessionTime,
                          String location)
    {
        return null;
    }

    /**
     * @param newSession
     * @return
     */
    @Override
    public
    ISession storeSession(ISession newSession)
    {
        return null;
    }

    /**
     * @param sessionID
     * @return
     */
    @Override
    public
    boolean deleteSession(int sessionID)
    {
        return false;
    }

    @Override
    public
    boolean updateSession(ISession session)
    {
        ISession updatedSession = null;
        boolean  result         = false;
        for (ISession s : sessions) {
            if (s.getSessionID() == session.getSessionID()) {
                s.setSessionLocation(session.getAtLocation());
                s.setStudent(session.getStudent());
                s.setTutor(session.getTutor());
                s.setStage(session.getStage());
                updatedSession = s;
                result         = true;
                break;
            }
        }
        if (updatedSession == null) {
            throw new NoSuchElementException();
        }
        return result;
    }

    /**
     * @return
     */
    @Override
    public
    Map<Integer, ISession> getSessions()
    {
        return null;
    }

    @Override
    public
    List<ISession> getSessionsByRangeForStudent(int StudentAID, TimeSlice range)
    {
        return sessions
                .stream()
                .filter(session -> range.canContain(session.getTime()) &&
                                   session.getStudent().getAccountID() ==
                                   StudentAID)
                .collect(Collectors.toList());
    }

    @Override
    public
    List<ISession> getSessionsByRangeForTutor(int StudentAID, TimeSlice range)
    {
        return sessions
                .stream()
                .filter(session -> range.canContain(session.getTime()) &&
                                   session.getTutor().getAccountID() ==
                                   StudentAID)
                .collect(Collectors.toList());
    }

    @Override
    public
    List<ISession> getPendingSessionRequests(int AID)
    {
        return sessions
                .stream()
                .filter(session -> session.getTutor().getAccountID() == AID &&
                                   session.getStage())
                .collect(Collectors.toList());
    }
}
