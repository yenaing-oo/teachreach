package comp3350.teachreach.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class Student extends User implements IStudent {
    private List<ISession> sessionsPendingForApproval;
    private List<ISession> scheduledSessions;

    public Student(String name,
                   String pronouns,
                   String major) {
        super( name, pronouns, major);
        sessionsPendingForApproval = new ArrayList<>();
        scheduledSessions = new ArrayList<>();
    }

    public List<ISession> getScheduledSessions() {
        return scheduledSessions;
    }

    public List<ISession> getSessionsPendingForApproval() {
        return sessionsPendingForApproval;
    }

    // This moves specified  approved Session to the approved List
    //  if it's found in the pending List.
    public boolean pendingSessionApproved(ISession approvedSession) {
        assert (approvedSession.getStage());
        final Predicate<ISession> sessionIDMatch = session ->
                session.getSessionID() == approvedSession.getSessionID();
        return sessionsPendingForApproval.removeIf(sessionIDMatch) &&
                scheduledSessions.add(
                        sessionsPendingForApproval
                                .stream()
                                .filter(sessionIDMatch)
                                .findFirst()
                                .orElseThrow(NoSuchElementException::new));
    }

    public Student addPendingSession(ISession newSession) {
        sessionsPendingForApproval.add(newSession);
        return this;
    }
}