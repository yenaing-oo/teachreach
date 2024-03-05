package comp3350.teachreach.logic.dataAccessObject;

import java.util.Collections;
import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.objects.ITutor;

public class AccessTutor {
    private ITutorPersistence tutorPersistence;
    private List<ITutor> tutors;
    private ITutor tutor;

    public AccessTutor() {
        tutorPersistence = Server.getTutorDataAccess();
        tutors = null;
        tutor = null;
    }

    public AccessTutor(final ITutorPersistence tutorPersistence) {
        this();
        this.tutorPersistence = tutorPersistence;
    }

    public List<ITutor> getTutors() {
        tutors = tutorPersistence.getTutors();
        return Collections.unmodifiableList(tutors);
    }

    public ITutor getTutorByEmail(String email) throws NullPointerException {
        if (tutors == null) {
            tutors = tutorPersistence.getTutors();
        }
        tutors.stream()
                .filter(t -> t.getEmail().equals(email))
                .findFirst()
                .ifPresentOrElse(t -> tutor = t, () -> {
                    tutor = null;
                    tutors = null;
                });
        return tutor;
    }

    public ITutor insertTutor(ITutor newTutor) {
        return tutorPersistence.storeTutor(newTutor);
    }

    public ITutor updateTutor(ITutor newTutor) {
        return tutorPersistence.updateTutor(newTutor);
    }
}
