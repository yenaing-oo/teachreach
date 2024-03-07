package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.ICourse;

public interface ITutoredCourse {
    List<ICourse> getTutorCourseByTID(int tutor_id);
    boolean storeTutorCourse(int tutorID, int sessionID);
}
