package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.objects.TimeSlice;

public interface ISessionPersistence {
    ISession storeSession(IStudent theStudent, ITutor theTutor,
                          TimeSlice sessionTime, String location);

    boolean deleteSession(ISession session);

    boolean updateSession(ISession session);

    List<ISession> getSessionsByRangeForStudent(
            String userEmail, TimeSlice range);

    List<ISession> getSessionsByRangeForTutor(
            String userEmail, TimeSlice range);

    List<ISession> getPendingSessionRequests(String userEmail);
}
