package comp3350.teachreach.logic;
import comp3350.teachreach.data.*;

public class Server {
    private static AccountStub accounts;
    private static CourseStub courses;
    private static SessionStub sessions;

    public static synchronized AccountStub getAccounts(){
        if (accounts == null){
            accounts = new AccountStub();
        }

        return accounts;
    }
    public static synchronized CourseStub getAourses(){
        if ( courses == null){
            courses = new CourseStub();
        }

        return  courses;
    }


    public static synchronized SessionStub getSessions(){
        if (sessions == null){
            sessions = new SessionStub();
        }

        return sessions;
    }




}
