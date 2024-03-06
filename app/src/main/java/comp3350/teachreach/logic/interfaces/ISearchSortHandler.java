package comp3350.teachreach.logic.interfaces;

import java.util.List;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ITutor;

public
interface ISearchSortHandler
{
    List<ITutor> getTutors();

    List<ITutor> getTutorsByCourse(Course course);

    List<ITutor> getTutorsByRating();

    // default ascending
    List<ITutor> getTutorsByHourlyRate();

    // overloaded method
    List<ITutor> getTutorsByHourlyRate(boolean reverseOrder);

    // excluded as still figuring out how to best represent availability
    //    List<Tutor> getTutorsByAvailability();
}
