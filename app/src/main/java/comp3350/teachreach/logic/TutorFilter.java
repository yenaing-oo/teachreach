package comp3350.teachreach.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorFilter
{
    AccessTutors            accessTutors  = new AccessTutors();
    AccessCourses           accessCourses = new AccessCourses();
    List<Predicate<ITutor>> conditions    = new ArrayList<>();

    public TutorFilter()
    {
    }

    private Predicate<ITutor> condition()
    {
        return conditions
                .stream()
                .reduce(Predicate::and)
                .orElseThrow(NoSuchElementException::new);
    }

    public TutorFilter setMinimumHourlyRate(double desiredHourlyRate)
    {
        conditions.add(t -> t.getHourlyRate() >= desiredHourlyRate);
        return this;
    }

    public TutorFilter setMaximumHourlyRate(double desiredHourlyRate)
    {
        conditions.add(t -> t.getHourlyRate() <= desiredHourlyRate);
        return this;
    }

    public TutorFilter setCourseString(String searchString)
    {
        conditions.add(t -> {
            ITutorProfileHandler tP = new TutorProfileHandler(t);
            return tP
                    .getCourses()
                    .stream()
                    .anyMatch(c -> c.getCourseName().contains(searchString) ||
                                   c.getCourseCode().contains(searchString));
        });
        return this;
    }
}
