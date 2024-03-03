package comp3350.teachreach.logic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.ISessionPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.logic.profile.AvailabilityManager;
import comp3350.teachreach.objects.ISession;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.TimeSlice;

public class BookingHandler {
    private final ITutor theTutor;
    private final AvailabilityManager availabilityManager;
    private ISessionPersistence sessionsDataAccess;
    private ITutorPersistence tutorsDataAccess;
    private IStudentPersistence studentsDataAccess;

    public BookingHandler(ITutor theTutor) {
        this.theTutor = theTutor;
        sessionsDataAccess = Server.getSessionDataAccess();
        tutorsDataAccess = Server.getTutorDataAccess();
        studentsDataAccess = Server.getStudentDataAccess();
        availabilityManager = new AvailabilityManager(theTutor);
    }

    public BookingHandler(
            ITutor theTutor,
            ITutorPersistence tutors,
            IStudentPersistence students,
            ISessionPersistence sessions) {
        this.theTutor = theTutor;
        sessionsDataAccess = sessions;
        tutorsDataAccess = tutors;
        studentsDataAccess = students;
        availabilityManager = new AvailabilityManager(theTutor);
    }

    public BookingHandler(
            String theTutorsEmail,
            ITutorPersistence tutors,
            IStudentPersistence students,
            ISessionPersistence sessions) {
        sessionsDataAccess = sessions;
        tutorsDataAccess = tutors;
        studentsDataAccess = students;
        this.theTutor = tutorsDataAccess.getTutorByEmail(theTutorsEmail)
                .orElseThrow(NoSuchElementException::new);
        availabilityManager = new AvailabilityManager(theTutor);
    }

//    public ArrayList<Session> getListOfSession() {
//        return sessionsDataAccess.getSessions();
//    }
//
//    // get tutor_> update-> retrieve availability
//    public boolean[][] tutorAvailability(Tutor tutor) //later not pass tutor?
//    //scenario: student click on tutor profile->check availability on tutor
//    {
//        // Check dates and time from tutor->profile->booking object->findout the tine and date
//        //1. access data
//        //ArrayList<tutor> ListofTutor = dataAccessTutor.getstubTutor();
//        Optional<ITutor> maybeTutor =
//                tutorsDataAccess.getTutorByEmail(tutor.getOwner().getEmail());
//        //2. retrieve data ( time, dates )
//        boolean[][] tutorAvailability = null;
//        if (maybeTutor.isPresent()) {
//            //tutorAvailability = maybeTutor.get().getAvailability();
//        }
//
//        //3. return object(booking) (list of objects?) (confirm and pending)
//        return tutorAvailability;
//    }


    //public Session createStudentRequests(Student student, Tutor tutor,
    //                                      int day, int month, int year,
    //int hour, String location)
    //scenario: student click on request ( with dates, time, location), then send to the booking object to
    //{
    //1. receive student request from UI
    //2. send condition:pending request to tutor (database)
    //if (available)
    //Session newSession = new Session(student, tutor, day, month, year,
    //    hour, location);
    //sessionsDataAccess.addSession(newSession);
    //3. set unavailability on tutor
    //tutor.setAvailability(day, hour, false);
    //return newSession;
    //}

    public Optional<ISession> requestNewSession(
            IStudent theStudent,
            int day, int month, int year,
            int hour, int minute,
            int durationInMinutes, String location) {
        TimeSlice sessionTime = TimeSlice.of(
                year, month, day, hour, minute, durationInMinutes);
        ISession resultSession = null;
        if (availabilityManager.isAvailableAt(sessionTime)) {
            resultSession = sessionsDataAccess.storeSession(
                    theStudent, theTutor, sessionTime, location);
        }
        return Optional.ofNullable(resultSession);
    }

    public List<ISession> getPendingSessionRequests() {
        return sessionsDataAccess.getPendingSessionRequests(theTutor.getOwner().getEmail());
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
//    public ArrayList<Session> retrieveAllStudentRequests(Tutor tutor)
//    //scenario: tutor on upcoming request UI would need to continuious show the student requests
//    {
//        //1. retrieve data from dataset
//        //ArrayList<Session>ListofSession = dataAccessBooking.getstubSession();
//        //2. return all pending booking
//        return sessionsDataAccess.searchSessionByTutor(tutor);
//
//    }

//    public void AnsweredRequest(Tutor tutor, Session session, boolean decision)
//    //scenario: tutor could answer request from student ( Accept/Reject )
//    {
//
//        //1.Receive answer from UI (object, or time, date, locaiton)
//        if (!decision) {
//            int date = session.getDay();
//            int hour = session.getHour();
//
//            sessionsDataAccess.removeSession(session);
//            //Reject, then available.
//            tutor.setAvailability(date, hour, true);
//        } else {
//            session.setStage(true);
//        }
//    }
    //2.Send it to database, like confimation


    //lower function
    //all get set
    /*
    public void addSessionInfo(Student student, Tutor tutor, int day, int month, int year, int hour, String location) {
        dataAccessBooking.addSessionInfo(student ,tutor, day, month, year, hour,location);
    }

    public void addSession(Session session) {
        dataAccessBooking.addSession(session);
    }*/
}
//make a handler
//with KT: he make the location showing on profile
// with aidan: change dataset : booking condiition + pending


