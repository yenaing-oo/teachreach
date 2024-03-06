package comp3350.teachreach.logic.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public interface ISearchSortHandler {
    List<ITutor> getTutors();

    List<ICourse> getCourses();

    List<ITutor> getTutorsByCourse(ICourse course);

    List<ITutor> getTutorsByRating();

    // default ascending
    List<ITutor> getTutorsByHourlyRateAsc();

    // overloaded method
    List<ITutor> getTutorsByHourlyRateDesc();

}
