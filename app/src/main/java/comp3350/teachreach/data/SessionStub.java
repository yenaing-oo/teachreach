package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class SessionStub implements ISessionPersistance {

    ArrayList<Session> sessions;
    AccountStub accountsDataAccess;
    StudentStub studentsDataAccess;
    TutorStub tutorsDataAccess;

    public SessionStub() {
        sessions = new ArrayList<>();

        accountsDataAccess = new AccountStub();
        studentsDataAccess = new StudentStub(accountsDataAccess);
        tutorsDataAccess = new TutorStub(accountsDataAccess);
    }

    @Override
    public void addSession(Session session) {
        this.sessions.add(session);
    }

    @Override
    public Session addSessionInfo(Student student,
                                  Tutor tutor,
                                  int day, int month, int year, int hour,
                                  String location) {
        Session newSession = new Session(
                student,
                tutor,
                day, month, year, hour,
                location);
        this.sessions.add(newSession);
        return newSession;
    }

    @Override
    public void removeSession(Session session) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).equals(session)) {
                sessions.remove(session);
            }
        }
    }

    @Override
    public ArrayList<Session> getSessions() {
        return this.sessions;
    }

    @Override
    public ArrayList<Session> searchSessionByTutorWithStage(Tutor tutor, boolean stage) {
        ArrayList<Session> Searched = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getTutor().equals(tutor)) {
                if (sessions.get(i).getStage() == stage) {
                    Searched.add(sessions.get(i));
                }
            }
        }
        return Searched;
    }

    @Override
    public ArrayList<Session> searchSessionByTutor(Tutor tutor) {
        ArrayList<Session> Searched = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getTutor().equals(tutor)) {
                Searched.add(sessions.get(i));
            }
        }
        return Searched;
    }
}
