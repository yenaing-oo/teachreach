package comp3350.teachreach.application;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.ICoursePersistence;
import comp3350.teachreach.data.ISessionPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.data.hsqldb.CourseHSQLDB;
import comp3350.teachreach.data.hsqldb.SessionHSQLDB;
import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorHSQLDB;

public class Server {
    private static IAccountPersistence accountDataAccess;

    private static IStudentPersistence studentDataAccess;
    private static ITutorPersistence tutorDataAccess;
    private static ICoursePersistence courseDataAccess;
    private static ISessionPersistence sessionDataAccess;

    public static synchronized IAccountPersistence getAccountDataAccess() {
        if (accountDataAccess == null) {
            accountDataAccess = new AccountHSQLDB(TRData.getDBPathName());
        }
        return accountDataAccess;
    }

    public static synchronized IStudentPersistence getStudentDataAccess() {
        if (studentDataAccess == null) {
            studentDataAccess = new StudentHSQLDB(TRData.getDBPathName());
        }
        return studentDataAccess;
    }

    public static synchronized ITutorPersistence getTutorDataAccess() {
        if (tutorDataAccess == null) {
            tutorDataAccess = new TutorHSQLDB(TRData.getDBPathName());
        }
        return tutorDataAccess;
    }

    public static synchronized ICoursePersistence getCourseDataAccess() {
        if (courseDataAccess == null) {
            courseDataAccess = new CourseHSQLDB(TRData.getDBPathName());
        }
        return courseDataAccess;
    }


    public static synchronized ISessionPersistence getSessionDataAccess() {
        if (sessionDataAccess == null) {
            sessionDataAccess = new SessionHSQLDB(TRData.getDBPathName());
        }
        return sessionDataAccess;
    }
}
