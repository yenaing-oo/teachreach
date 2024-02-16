package comp3350.teachreach.logic;

import java.util.List;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;

public interface ISearchSortHandler {
    public List<Tutor> getTutors();

    public List<Tutor> getTutorsByCourse(Course course);

    public List<Tutor> getTutorsByRating();

    // default ascending
    public List<Tutor> getTutorsByHourlyRate();

    // overloaded method
    public List<Tutor> getTutorsByHourlyRate(boolean reverseOrder);

    // excluded as still figuring out how to best represent availability
//    public List<Tutor> getTutorsByAvailability();

}
