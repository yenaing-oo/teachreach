package comp3350.teachreach.logic;

import java.util.List;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;

public interface ISearchSortHandler {
    List<Tutor> getTutors();

    List<Tutor> getTutorsByCourse(Course course);

    List<Tutor> getTutorsByRating();

    // default ascending
    List<Tutor> getTutorsByHourlyRate();

    // overloaded method
    List<Tutor> getTutorsByHourlyRate(boolean reverseOrder);

    // excluded as still figuring out how to best represent availability
//    List<Tutor> getTutorsByAvailability();

}
