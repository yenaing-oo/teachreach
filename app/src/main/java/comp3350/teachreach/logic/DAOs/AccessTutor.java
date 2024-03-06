package comp3350.teachreach.logic.DAOs;

import java.util.Collections;
import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class AccessTutor
{
    private ITutorPersistence tutorPersistence;
    private List<ITutor>      tutors;
    private ITutor            tutor;

    public
    AccessTutor()
    {
        tutorPersistence = Server.getTutorDataAccess();
        tutors           = null;
        tutor            = null;
    }

    public
    AccessTutor(final ITutorPersistence tutorPersistence)
    {
        this();
        this.tutorPersistence = tutorPersistence;
    }

    public
    List<ITutor> getTutors()
    {
        tutors = tutorPersistence.getTutors();
        return Collections.unmodifiableList(tutors);
    }

    public
    ITutor getTutorByAID(int AID) throws NullPointerException
    {
        if (tutors == null) {
            tutors = tutorPersistence.getTutors();
        }
        tutors
                .stream()
                .filter(t -> t.getAccountID() == AID)
                .findFirst()
                .ifPresentOrElse(t -> tutor = t, () -> {
                    tutor  = null;
                    tutors = null;
                });
        return tutor;
    }

    public
    ITutor insertTutor(ITutor newTutor)
    {
        return tutorPersistence.storeTutor(newTutor);
    }

    public
    ITutor updateTutor(ITutor newTutor)
    {
        return tutorPersistence.updateTutor(newTutor);
    }
}
