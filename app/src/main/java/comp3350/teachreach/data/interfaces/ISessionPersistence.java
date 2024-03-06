package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public
interface ISessionPersistence
{
    ISession storeSession(IStudent theStudent,
                          ITutor theTutor,
                          TimeSlice sessionTime,
                          String location);

    boolean deleteSession(ISession session);

    boolean updateSession(ISession session);

    List<ISession> getSessionsByRangeForStudent(int studentID, TimeSlice range);

    List<ISession> getSessionsByRangeForTutor(int tutorID, TimeSlice range);

    List<ISession> getPendingSessionRequests(int tutorID);
}
