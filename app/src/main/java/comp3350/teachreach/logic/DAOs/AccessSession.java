package comp3350.teachreach.logic.DAOs;

import java.util.List;
import java.util.NoSuchElementException;

import comp3350.teachreach.application.Server;

import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class AccessSession {
    private static ISessionPersistence sessionPersistence;
    private static List<ISession> sessions;
    //private ISession session;

    public AccessSession() {
        AccessSession.sessionPersistence = Server.getSessionDataAccess();

    }

    public AccessSession(ISessionPersistence sessionDataAccess) {
        AccessSession.sessionPersistence = sessionDataAccess;
    }
    public static  boolean deleteSession(ISession session) {

        return sessionPersistence.deleteSession((session));
    }

    public static  ISession storeSession(IStudent theStudent, ITutor theTutor,
                                         TimeSlice sessionTime, String location) {

        return sessionPersistence.storeSession(theStudent,theTutor,sessionTime,location);
    }

    public static boolean updateSession(ISession session) {

        return sessionPersistence.updateSession(session);
    }
    public static  List<ISession> getSessionsByRangeForStudent(
            String userEmail, TimeSlice range) {

        return sessionPersistence.getSessionsByRangeForStudent(userEmail,range);
    }

    public static List<ISession> getSessionsByRangeForTutor(
            String userEmail, TimeSlice range){

        return sessionPersistence.getSessionsByRangeForTutor(userEmail,range);
    }

    public static  List<ISession> getPendingSessionRequests(String userEmail){

        return sessionPersistence.getPendingSessionRequests(userEmail);
    }


}
