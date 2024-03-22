package comp3350.teachreach.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.interfaces.ITutorFilter;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorFilter implements ITutorFilter
{
    AccessTutors            accessTutors  = new AccessTutors();
    AccessCourses           accessCourses = new AccessCourses();
    List<Predicate<ITutor>> conditionsAnd = new ArrayList<>();
    List<Predicate<ITutor>> conditionsOr  = new ArrayList<>();

    public TutorFilter()
    {
    }

    private Predicate<ITutor> condition()
    {
        Predicate<ITutor> cAnd = conditionsAnd
                .stream()
                .reduce(Predicate::and)
                .orElse(t -> true);
        Predicate<ITutor> cOr = conditionsOr
                .stream()
                .reduce(Predicate::or)
                .orElse(t -> true);
        return cAnd.and(cOr);
    }

    @Override
    public TutorFilter setMinimumHourlyRate(double desiredHourlyRate)
    {
        conditionsAnd.add(t -> t.getHourlyRate() >= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setMaximumHourlyRate(double desiredHourlyRate)
    {
        conditionsAnd.add(t -> t.getHourlyRate() <= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setCourse(String courseString)
    {
        conditionsOr.add(t -> {
            ITutorProfileHandler tP = new TutorProfileHandler(t);
            return tP
                    .getCourses()
                    .stream()
                    .anyMatch(c -> c.getCourseCode().contains(courseString) ||
                                   c.getCourseName().contains(courseString));
        });
        return this;
    }

    @Override
    public TutorFilter setMajor(String searchString)
    {
        conditionsOr.add(t -> {
            ITutorProfileHandler tP = new TutorProfileHandler(t);
            return tP.getUserMajor().contains(searchString);
        });
        return this;
    }

    @Override
    public TutorFilter setName(String searchString)
    {
        conditionsOr.add(t -> {
            ITutorProfileHandler tP = new TutorProfileHandler(t);
            return tP.getUserName().contains(searchString);
        });
        return this;
    }

    @Override
    public TutorFilter setLocation(String searchString)
    {
        conditionsOr.add(t -> {
            ITutorProfileHandler tP = new TutorProfileHandler(t);
            return tP.getPreferredLocations().contains(searchString);
        });
        return this;
    }

    @Override
    public Function<List<ITutor>, List<ITutor>> filterFunc()
    {
        return t -> t
                .stream()
                .filter(this.condition())
                .collect(Collectors.toList());
    }
}
