package comp3350.teachreach.logic.DAOs;

import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutoredCourse;
import comp3350.teachreach.objects.interfaces.ICourse;

public class AccessTutoredCourses
{
    private static ITutoredCourse TutoredCoursesPersistence;

    public AccessTutoredCourses(){
        TutoredCoursesPersistence = Server.getTutorCourseAccess();
    }

    public AccessTutoredCourses(ITutoredCourse tutorCourseAccess){
        AccessTutoredCourses.TutoredCoursesPersistence = tutorCourseAccess;
    }


    public List<ICourse> getTutorCourseByTID(int tutorID){
        try {
            return TutoredCoursesPersistence.getTutorCourseByTID(tutorID);
        }
        catch (final Exception e) {
            throw new DataAccessException(
                    "Failed to get tutor tutored courses by tutor's " + "id", e);
        }
    }

    public boolean storeTutorCourse(int tutorID, int sessionID) {
        try{
            return TutoredCoursesPersistence.storeTutorCourse(tutorID, sessionID);
        }
        catch (final Exception e) {
            throw new DataAccessException("Failed to store tutor tutored course!", e);
        }

    }

}
