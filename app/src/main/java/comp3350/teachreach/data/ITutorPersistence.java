package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.Tutor;

public interface ITutorPersistence {
    Tutor storeTutor(Tutor newTutor);

    Tutor updateTutor(Tutor newTutor);

    ArrayList<Tutor> getTutors();

    ArrayList<Tutor> getTutorsByName(String name);
}
