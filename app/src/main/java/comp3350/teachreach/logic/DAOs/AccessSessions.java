package comp3350.teachreach.logic.DAOs;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ISession;

public class AccessSessions
{
    private static ISessionPersistence    sessionPersistence;
    private static Map<Integer, ISession> sessions       = null;
    private final  Predicate<ISession>    notYetAccepted = s -> {
        return !s.getAcceptedStatus();
    };

    public AccessSessions()
    {
        AccessSessions.sessionPersistence = Server.getSessionDataAccess();
        AccessSessions.sessions           = sessionPersistence.getSessions();
    }

    public AccessSessions(ISessionPersistence sessionDataAccess)
    {
        AccessSessions.sessionPersistence = sessionDataAccess;
        AccessSessions.sessions           = sessionDataAccess.getSessions();
    }

    public boolean deleteSession(ISession session)
    {
        try {
            boolean result
                    = sessionPersistence.deleteSession(session.getSessionID());
            if (result) {
                sessions = sessionPersistence.getSessions();
            }
            return result;
        } catch (final Exception e) {
            throw new DataAccessException("Failed to delete session", e);
        }
    }

    public ISession storeSession(int studentID,
                                 int tutorID,
                                 TimeSlice sessionTime,
                                 String location)
    {
        try {
            ISession newSession = sessionPersistence.storeSession(studentID,
                                                                  tutorID,
                                                                  sessionTime,
                                                                  location);
            sessions = sessionPersistence.getSessions();
            return newSession;
        } catch (final Exception e) {
            throw new DataAccessException("Failed to store session!", e);
        }
    }

    public Map<Integer, ISession> getSessions()
    {
        if (sessions == null) {
            sessions = sessionPersistence.getSessions();
        }
        return sessions;
    }

    public ISession updateSession(ISession session)
    {
        try {
            session  = sessionPersistence.updateSession(session);
            sessions = sessionPersistence.getSessions();
            return session;
        } catch (final Exception e) {
            throw new DataAccessException("Failed to update session!", e);
        }
    }

    public List<ISession> getSessionsByTutorID(int tutorID)
    {
        try {
            return AccessSessions.sessions
                    .values()
                    .stream()
                    .filter(s -> s.getSessionTutorID() == tutorID)
                    .collect(Collectors.toList());
        } catch (final Exception e) {
            throw new DataAccessException(
                    "Failed to get sessions by tutor's " + "id", e);
        }
    }

    public List<ISession> getPendingSessionsByTutorID(int tutorID)
    {
        try {
            Predicate<ISession> tutorIDMatch = s -> {
                return s.getSessionTutorID() == tutorID;
            };
            return AccessSessions.sessions
                    .values()
                    .stream()
                    .filter(notYetAccepted.and(tutorIDMatch))
                    .collect(Collectors.toList());
        } catch (final Exception e) {
            throw new DataAccessException(
                    "Failed to get pending sessions by tutorID",
                    e);
        }
    }

    public List<ISession> getPendingSessionsByStudentID(int studentID)
    {
        try {
            Predicate<ISession> studentIDMatch = s -> s.getSessionStudentID() ==
                                                      studentID;
            return AccessSessions.sessions
                    .values()
                    .stream()
                    .filter(notYetAccepted.and(studentIDMatch))
                    .collect(Collectors.toList());
        } catch (final Exception e) {
            throw new DataAccessException(
                    "Failed to get pending sessions by studentID",
                    e);
        }
    }

    public List<ISession> getSessionsByStudentID(int studentID)
    {
        try {
            return AccessSessions.sessions
                    .values()
                    .stream()
                    .filter(s -> s.getSessionStudentID() == studentID)
                    .collect(Collectors.toList());
        } catch (final Exception e) {
            throw new DataAccessException(
                    "Failed to get sessions by student's id",
                    e);
        }
    }
}
