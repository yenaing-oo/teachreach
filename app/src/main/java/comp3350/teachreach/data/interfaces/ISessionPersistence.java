package comp3350.teachreach.data.interfaces;

import java.util.Map;

import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public
interface ISessionPersistence {
    ISession storeSession(IStudent student,
                          ITutor tutor,
                          ITimeSlice timeRange,
                          String location);

    ISession storeSession(ISession newSession);

    boolean deleteSession(ISession session);

    ISession updateSession(ISession session);

    Map<Integer, ISession> getSessions();
}
