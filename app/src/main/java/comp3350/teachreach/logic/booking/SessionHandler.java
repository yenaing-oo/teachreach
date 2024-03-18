package comp3350.teachreach.logic.booking;

import java.util.List;

import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.data.interfaces.ITutorAvailabilityPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessSessions;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class SessionHandler {
    private final ITutorAvailabilityManager tutorAvailabilityManager;
    private final AccessSessions accessSessions;
    private final AccessTutors accessTutors;


    public SessionHandler() {
        this.accessSessions = new AccessSessions();
        this.accessTutors = new AccessTutors();
        this.tutorAvailabilityManager = new TutorAvailabilityManager();
    }

    public SessionHandler(ISessionPersistence sessions,
                          ITutorPersistence tutors,
                          ITutorAvailabilityPersistence tutorAvailability) {
        this.accessSessions = new AccessSessions(sessions);
        this.accessTutors = new AccessTutors(tutors);
        this.tutorAvailabilityManager = new TutorAvailabilityManager(tutorAvailability);
    }

    public ISession bookSession(ISession session) {
        ITutor tutor = accessTutors.getTutorByTutorID(session.getSessionTutorID());
        if (tutorAvailabilityManager.isAvailableAt(tutor, session.getTime())) {
            ISession resultSession = accessSessions.storeSession(session);
            assert (resultSession != null);
            return resultSession;
        } else {
            throw new RuntimeException("Failed to request new session");
        }
    }

    public List<ISession> getSessions(IStudent student) {
        return accessSessions.getSessions(student);
    }

    public List<ISession> getSessions(ITutor tutor) {
        return accessSessions.getSessions(tutor);
    }

    public List<ISession> getPendingSessions(IStudent student) {
        return accessSessions.getPendingSessions(student);
    }

    public List<ISession> getPendingSessions(ITutor tutor) {
        return accessSessions.getPendingSessions(tutor);
    }

    public List<ISession> getAcceptedSessions(IStudent student) {
        return accessSessions.getAcceptedSessions(student);
    }

    public List<ISession> getAcceptedSessions(ITutor tutor) {
        return accessSessions.getAcceptedSessions(tutor);
    }

    public List<ISession> getRejectedSessions(IStudent student) {
        return accessSessions.getRejectedSessions(student);
    }

    public List<ISession> getRejectedSessions(ITutor tutor) {
        return accessSessions.getRejectedSessions(tutor);
    }
}

