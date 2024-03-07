package comp3350.teachreach.application;

import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.data.hsqldb.CourseHSQLDB;
import comp3350.teachreach.data.hsqldb.SessionHSQLDB;
import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorAvailabilityHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorLocationHSQLDB;
import comp3350.teachreach.data.hsqldb.TutoredCourseHSQLDB;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorAvailability;
import comp3350.teachreach.data.interfaces.ITutorLocation;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.data.interfaces.ITutoredCourse;

public
class Server
{
    private static IAccountPersistence accountDataAccess;
    private static IStudentPersistence studentDataAccess;
    private static ITutorPersistence   tutorDataAccess;
    private static ICoursePersistence  courseDataAccess;
    private static ISessionPersistence sessionDataAccess;
    private static ITutorAvailability  tutorAvailabilityAccess;
    private static ITutoredCourse      tutoredCourseAccess;
    private static ITutorLocation      tutorLocationAccess;

    public static synchronized
    IAccountPersistence getAccountDataAccess()
    {
        if (accountDataAccess == null) {
            accountDataAccess = new AccountHSQLDB(TRData.getDBPathName());
        }
        return accountDataAccess;
    }

    public static synchronized
    IStudentPersistence getStudentDataAccess()
    {
        if (studentDataAccess == null) {
            studentDataAccess = new StudentHSQLDB(TRData.getDBPathName());
        }
        return studentDataAccess;
    }

    public static synchronized
    ITutorPersistence getTutorDataAccess()
    {
        if (tutorDataAccess == null) {
            tutorDataAccess = new TutorHSQLDB(TRData.getDBPathName());
        }
        return tutorDataAccess;
    }

    public static synchronized
    ICoursePersistence getCourseDataAccess()
    {
        if (courseDataAccess == null) {
            courseDataAccess = new CourseHSQLDB(TRData.getDBPathName());
        }
        return courseDataAccess;
    }

    public static synchronized
    ISessionPersistence getSessionDataAccess()
    {
        if (sessionDataAccess == null) {
            sessionDataAccess = new SessionHSQLDB(TRData.getDBPathName());
        }
        return sessionDataAccess;
    }
    public static synchronized
    ITutoredCourse getTutorCourseAccess()
    {
        if (tutoredCourseAccess == null) {
            tutoredCourseAccess = new TutoredCourseHSQLDB(TRData.getDBPathName());
        }
        return tutoredCourseAccess;
    }
    public static synchronized
    ITutorLocation getTutorLocationAccess()
    {
        if (tutorLocationAccess == null) {
            tutorLocationAccess = new TutorLocationHSQLDB(TRData.getDBPathName());
        }
        return tutorLocationAccess;
    }
    public static synchronized
    ITutorAvailability getTutorAvailabilityAccess()
    {
        if (tutorAvailabilityAccess== null) {
            tutorAvailabilityAccess = new TutorAvailabilityHSQLDB(TRData.getDBPathName());
        }
        return tutorAvailabilityAccess;
    }
}
