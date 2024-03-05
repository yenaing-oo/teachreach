package comp3350.teachreach.data;

import java.util.List;

import comp3350.teachreach.objects.ISession;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.ITutor;
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
