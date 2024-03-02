package comp3350.teachreach.logic;
import comp3350.teachreach.data.*;

public class Server {
    private static IAccountPersistence accountsDataAccess;

    private static IStudentPersistence studentsDataAccess;
    private static ITutorPersistence tutorsDataAccess;
    private static CourseStub courses;
    private static SessionStub sessions;

    public static synchronized IAccountPersistence getAccountDataAccess(){
        if (accountsDataAccess == null){
            accountsDataAccess = new AccountStub();
        }

        return accountsDataAccess;
    }
    public static synchronized IStudentPersistence getStudentDataAccess(){
        if (studentsDataAccess == null){
            studentsDataAccess = new StudentStub(getAccountDataAccess());
        }

        return studentsDataAccess;
    }

    public static synchronized ITutorPersistence getTutorDataAccess(){
        if (tutorsDataAccess == null){
            tutorsDataAccess = new TutorStub(getAccountDataAccess());
        }

        return tutorsDataAccess;
    }

    public static synchronized CourseStub getCourses(){
        if ( courses == null){
            courses = new CourseStub();
        }

        return  courses;
    }


    public static synchronized SessionStub getSessionDataAccess(){
        if (sessions == null){
            sessions = new SessionStub();
        }

        return sessions;
    }
}
