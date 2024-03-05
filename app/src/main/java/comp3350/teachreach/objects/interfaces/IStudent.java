package comp3350.teachreach.objects.interfaces;

import java.util.List;

public interface IStudent extends IUser {
    List<ISession> getScheduledSessions();

    List<ISession> getSessionsPendingForApproval();

    IStudent addPendingSession(ISession newSession);
}
