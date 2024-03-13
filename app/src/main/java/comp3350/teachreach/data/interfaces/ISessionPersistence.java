package comp3350.teachreach.data.interfaces;

import java.util.Map;

import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ISession;

public
interface ISessionPersistence
{
    ISession storeSession(int studentID,
                          int tutorID,
                          TimeSlice sessionTime,
                          String location);

    ISession storeSession(ISession newSession);

    boolean deleteSession(int sessionID);

    ISession updateSession(ISession session);

    Map<Integer, ISession> getSessions();
}
