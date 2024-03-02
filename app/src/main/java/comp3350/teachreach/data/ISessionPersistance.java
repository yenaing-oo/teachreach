package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public interface ISessionPersistance {
    void addSession(Session session);

    Session addSessionInfo(Student student,
                           Tutor tutor,
                           int day, int month, int year, int hour,
                           String location);

    void removeSession(Session session);

    ArrayList<Session> getSessions();

    ArrayList<Session> searchSessionByTutorWithStage(Tutor tutor, boolean stage);

    ArrayList<Session> searchSessionByTutor(Tutor tutor);
}
