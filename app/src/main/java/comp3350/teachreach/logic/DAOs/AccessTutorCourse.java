package comp3350.teachreach.logic.DAOs;

import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class AccessTutorCourse
{
    private final ITutorPersistence  tutorPersistence;
    private final ICoursePersistence coursePersistence;

    private final List<ITutor>  tutors;
    private final List<ICourse> courses;

    public
    AccessTutorCourse()
    {
        tutorPersistence  = Server.getTutorDataAccess();
        coursePersistence = Server.getCourseDataAccess();
        tutors            = tutorPersistence.getTutors();
        courses           = coursePersistence.getCourses();
    }

    public
    List<ICourse> getTutoredCourses(int id)
    {
        return null;
    }
}
