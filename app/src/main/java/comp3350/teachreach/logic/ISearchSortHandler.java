package comp3350.teachreach.logic;

import java.util.List;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;

public interface ISearchSortHandler {
    List<Tutor> getTutors();

    List<Tutor> getTutorsByCourse(Course course);

    List<Tutor> getTutorsByHighestRating();

    List<Tutor> getTutorsByHourlyRateAsc();

    List<Tutor> getTutorsByHourlyRateDesc();

    // excluded for now as still figuring out how to best represent availability
//    List<Tutor> getTutorsByAvailability();

}
