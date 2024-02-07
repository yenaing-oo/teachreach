package comp3350.teachreach.logic;

import java.util.ArrayList;

import comp3350.teachreach.objects.Session;
import comp3350.teachreach.data.*;
import comp3350.teachreach.objects.*;

public class BookingHandler {

    private SessionStub dataAccessBooking;
    private IAccountPersistence dataAccessTutor;


    public BookingHandler() {
        //assume we have service handler
        dataAccessBooking = Server.getSessions();
        dataAccessTutor = Server.getAccounts();
    }

    public ArrayList<Tutor> getListofTutor() {
        return dataAccessTutor.getTutors();
    }

    public ArrayList<Session> getListofSession() {
        return dataAccessBooking.getStubSessions();
    }

    public boolean[][] tutorAvailability(Tutor tutor)
    //scenario: student click on tutor profile->check availability on tutor
    {
        // Check dates and time from tutor->profile->booking object->findout the tine and date
        //1. access data
        //ArrayList<tutor> ListofTutor = dataAccessTutor.getstubTutor();
        Tutor Searched = null;
        Searched = dataAccessTutor.getTutorByEmail(tutor.getEmail());
        //2. retrieve data ( time, dates )
        boolean[][] TutorAvailability = null;
        if (Searched != null) {
            TutorAvailability = Searched.getAvailability();
        }

        //3. return object(booking) (list of objects?) (confirm and pending)
        return TutorAvailability;
    }

    public void storeStudentRequests(Student student, Tutor tutor, int day, int month, int year, int hour, String location)
    //scenario: student click on request ( with dates, time, location), then send to the booking object to
    {
        //1. receive student request from UI
        //2. send condition:pending request to tutor (database)
        Session newSession = new Session(student, tutor, day, month, year, hour, location);
        //3. set unavailability on tutor
        tutor.setAvailability(day, hour, false);
    }


    public ArrayList<Session> retrievePendingOrAcceptStudentRequests(Tutor tutor, Boolean stage)
    //scenario: tutor on upcoming request UI would need to continuious show the student requests
    {
        //1. retrieve data from dataset
        //2. return all pending booking
        return dataAccessBooking.searchSessionbyTutorwithStage(tutor, stage);
    }

    /*
        public void retrieveAcceptedStudentRequests(Tutor tutor)
        //scenario: tutor on upcoming request UI would need to continuious show the student requests
        {
            //1. retrieve data from dataset
            ArrayList<Session> ListofSession = dataAccessBooking.getstubSession();
            //2. return all pending booking
            ArrayList<Session> Searched;
            for (int i = 0; i < ListofSession.size(); i++)
            {
                if(ListofSession[i].getTutor().equals(tutor))
                {
                    if(ListofSession[i].accepted == true)
                    Searched.add(ListofSession[i]);
                }
            }

            return Searched;

        }
    */
    public ArrayList<Session> retrieveAllStudentRequests(Tutor tutor)
    //scenario: tutor on upcoming request UI would need to continuious show the student requests
    {
        //1. retrieve data from dataset
        //ArrayList<Session>ListofSession = dataAccessBooking.getstubSession();
        //2. return all pending booking
        return dataAccessBooking.searchSessionbyTutor(tutor);

    }

    public void AnsweredRequest(Tutor tutor, Session session, boolean decision)
    //scenario: tutor could answer request from student ( Accept/Reject )
    {

        //1.Receive answer from UI (object, or time, date, locaiton)
        if (!decision) {
            int date = session.getDay();
            int hour = session.getHour();

            dataAccessBooking.removeSession(session);
            //Reject, then available.
            tutor.setAvailability(date, hour, true);
        } else  {
            session.setStage(true);
        }
    }
    //2.Send it to database, like confimation


    //lower function
    //all get set
    public void addSessionInfo(Student student, Tutor tutor, int day, int month, int year, int hour, String location){
        dataAccessBooking.addSessionInfo(student ,tutor, day, month, year, hour,location);
    }

    public void addSession(Session session) {
        dataAccessBooking.addSession(session);
    }
}
//make a handler
//with KT: he make the location showing on profile
// with aidan: change dataset : booking condiition + pending


