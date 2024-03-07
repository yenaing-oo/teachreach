package comp3350.teachreach.logic.DAOs;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutoredCourse;

public class AccessTutorCourse
{
    private static ITutoredCourse TutoredCoursePersistence;

    public AccessTutorCourse(){
        TutoredCoursePersistence = Server.getTutorCourseAccess();
    }

    public AccessTutorCourse(ITutoredCourse tutorCourseAccess){
        AccessTutorCourse.TutoredCoursePersistence = tutorCourseAccess;
    }

}
