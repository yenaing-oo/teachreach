package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class SessionStub {

    ArrayList<Session> sessions;
    ArrayList<Student> students;
    AccountStub accounts;


    ArrayList<Tutor> tutors;

    public SessionStub() {
        sessions = new ArrayList<>();
        students = new ArrayList<>();

        accounts = new AccountStub();


        students = accounts.getStudents();
        tutors = accounts.getTutors();

        Session session1 = new Session(students.get(0), tutors.get(3), 26, 5, 11, 3, "Library");
        //Session session2 = new Session(students.get(1), tutors.get(3), 27, 5, 11, 3,"School");
        sessions.add(session1);
        //sessions.add(session2);
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

    public ArrayList<Tutor> getTutorByName(Tutor tutor) {
        return accounts.getTutorsByName(tutor.getName());
    }

    public Student getStudentByEmail(String email){
        return accounts.getStudentByEmail(email);
    }

    public ArrayList<Session> searchSessionByTutorWithStage(Tutor tutor, boolean stage){
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

    public ArrayList<Session> searchSessionByTutor(Tutor tutor){
        ArrayList<Session> Searched = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            if(sessions.get(i).getTutor().equals(tutor)) {
                    Searched.add(sessions.get(i));
            }
        }
        return Searched;
    }
}
