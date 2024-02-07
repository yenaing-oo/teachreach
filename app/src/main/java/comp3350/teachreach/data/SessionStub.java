package comp3350.teachreach.data;
import comp3350.teachreach.objects.*;
import java.util.ArrayList;
import java.util.Random;

public class SessionStub {

    ArrayList<Session> sessions = new ArrayList<>();

    AccountStub accounts;
    ArrayList<Student> students;

    ArrayList<Tutor> tutors;
    public SessionStub(){

        students = accounts.getStudents();
        tutors = accounts.getTutors();

        Session session1 = new Session(students.get(1), tutors.get(3), 26, 5, 11, 3,"Library");

        sessions.add(session1);
    }


    public void addSession(Session session) {
        this.sessions.add(session);
    }

    //add price next time
    public void addSessionInfo(Student student, Tutor tutor, int day, int month, int year, int hour, String location){
        this.sessions.add(new Session(student ,tutor, day, month, year, hour,location));
    }


    public void removeSession(Session session) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).equals(session)) {
                sessions.remove(session);
            }
        }
    }
    //find user!
    public ArrayList<Session> getStubSessions() {
        return this.sessions;
    }

    public Tutor searchTutorbyName(Tutor tutor) {
        return accounts.searchTutorbyName(tutor);
    }

    public ArrayList<Session> searchSessionbyTutorwithStage(Tutor tutor, boolean stage){
        ArrayList<Session> Searched = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            if(sessions.get(i).getTutor().equals(tutor)) {
                if(sessions.get(i).getStage() == stage ) {
                        Searched.add(sessions.get(i));
                }
            }
        }
        return Searched;
    }

    public ArrayList<Session> searchSessionbyTutor(Tutor tutor){
        ArrayList<Session> Searched = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            if(sessions.get(i).getTutor().equals(tutor)) {
                    Searched.add(sessions.get(i));
            }
        }
        return Searched;
    }
}
