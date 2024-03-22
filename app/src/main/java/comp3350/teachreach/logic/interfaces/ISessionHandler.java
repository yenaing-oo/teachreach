package comp3350.teachreach.logic.interfaces;

import java.util.List;

import comp3350.teachreach.logic.exceptions.availability.TutorAvailabilityManagerException;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public interface ISessionHandler {
    ISession bookSession(ISession session) throws TutorAvailabilityManagerException;

    List<ISession> getSessions(IStudent student);

    List<ISession> getSessions(ITutor tutor);

    List<ISession> getPendingSessions(IStudent student);

    List<ISession> getPendingSessions(ITutor tutor);

    List<ISession> getAcceptedSessions(IStudent student);

    List<ISession> getAcceptedSessions(ITutor tutor);

    List<ISession> getRejectedSessions(IStudent student);

    List<ISession> getRejectedSessions(ITutor tutor);
}
