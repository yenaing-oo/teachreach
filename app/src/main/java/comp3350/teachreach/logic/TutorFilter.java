package comp3350.teachreach.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.interfaces.ITutorFilter;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorFilter implements ITutorFilter
{
    Map<ITutor, TutorProfileHandler> tutorProfileMap = new HashMap<>();
    List<Predicate<ITutor>>          condAnd         = new ArrayList<>();
    List<Predicate<ITutor>>          condOr          = new ArrayList<>();

    public TutorFilter()
    {
        Server
                .getTutorDataAccess()
                .getTutors()
                .values()
                .forEach(t -> tutorProfileMap.put(t,
                                                  new TutorProfileHandler(t)));
    }

    public TutorFilter(ITutorPersistence persistence)
    {
        persistence
                .getTutors()
                .values()
                .forEach(t -> tutorProfileMap.put(t,
                                                  new TutorProfileHandler(t)));
    }

    @Override
    public TutorFilter New()
    {
        return new TutorFilter();
    }

    @Override
    public TutorFilter setMinimumAvgRating(double minimumAvgRating)
    {
        condAnd.add(t -> tutorProfileMap
                                 .getOrDefault(t, new TutorProfileHandler(t))
                                 .getAvgReview() >= minimumAvgRating);
        return this;
    }

    @Override
    public TutorFilter setMinimumHourlyRate(double desiredHourlyRate)
    {
        condAnd.add(t -> t.getHourlyRate() >= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setMaximumHourlyRate(double desiredHourlyRate)
    {
        condAnd.add(t -> t.getHourlyRate() <= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setCourse(String courseString)
    {
        condOr.add(t -> tutorProfileMap
                .getOrDefault(t, new TutorProfileHandler(t))
                .getCourses()
                .stream()
                .anyMatch(c -> c.getCourseCode().contains(courseString) ||
                               c.getCourseName().contains(courseString)));
        return this;
    }

    @Override
    public TutorFilter setMajor(String searchString)
    {
        condOr.add(t -> tutorProfileMap
                .getOrDefault(t, new TutorProfileHandler(t))
                .getUserMajor()
                .contains(searchString));
        return this;
    }

    @Override
    public TutorFilter setName(String searchString)
    {
        condOr.add(t -> tutorProfileMap
                .getOrDefault(t, new TutorProfileHandler(t))
                .getUserName()
                .contains(searchString));
        return this;
    }

    @Override
    public TutorFilter setLocation(String searchString)
    {
        condOr.add(t -> tutorProfileMap
                .getOrDefault(t, new TutorProfileHandler(t))
                .getPreferredLocations()
                .contains(searchString));
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

    private Predicate<ITutor> condition()
    {
        Predicate<ITutor> conditionAnd = condAnd
                .stream()
                .reduce(Predicate::and)
                .orElse(t -> true);
        Predicate<ITutor> conditionOr = condOr
                .stream()
                .reduce(Predicate::or)
                .orElse(t -> true);
        return conditionAnd.and(conditionOr);
    }
}
