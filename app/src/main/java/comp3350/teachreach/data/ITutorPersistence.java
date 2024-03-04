package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.Tutor;

public interface ITutorPersistence {
    ITutor storeTutor(ITutor newTutor) throws RuntimeException;

    ITutor updateTutor(ITutor newTutor) throws RuntimeException;

    Optional<ITutor> getTutorByEmail(String email);

    List<ITutor> getTutors();

    List<ITutor> getTutorsByName(String name);
}
