package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.objects.ISession;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.Tutor;

public interface ISessionPersistence {
    ISession storeSession(IStudent theStudent, ITutor theTutor,
                          TimeSlice sessionTime, String location);
    ISessionPersistence deleteSession(ISession session);
    ISession updateSession(ISession session);

    List<ISession> getSessionsByRangeForStudent(
            String userEmail, TimeSlice range);
    List<ISession> getSessionsByRangeForTutor(
            String userEmail, TimeSlice range);
    List<ISession> getPendingSessionRequests(String userEmail);
}
