package comp3350.teachreach.logic;

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

    Map<ConditionFilterTag, Predicate<ITutor>> conditions = new HashMap<>();
    Map<SearchFilterTag, Predicate<ITutor>>    searches   = new HashMap<>();

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

    public static TutorFilter New()
    {
        return new TutorFilter();
    }

    @Override
    public TutorFilter Reset()
    {
        conditions.clear();
        searches.clear();
        return this;
    }

    @Override
    public boolean getMinimumAvgRatingState()
    {
        return conditions.containsKey(ConditionFilterTag.minReview);
    }

    @Override
    public boolean getMinimumHourlyRateState()
    {
        return conditions.containsKey(ConditionFilterTag.minPrice);
    }

    @Override
    public boolean getMaximumHourlyRateState()
    {
        return conditions.containsKey(ConditionFilterTag.maxPrice);
    }

    @Override
    public TutorFilter clearMinimumAvgRating()
    {
        conditions.remove(ConditionFilterTag.minReview);
        return this;
    }

    @Override
    public TutorFilter clearMinimumHourlyRate()
    {
        conditions.remove(ConditionFilterTag.minPrice);
        return this;
    }

    @Override
    public TutorFilter clearMaximumHourlyRate()
    {
        conditions.remove(ConditionFilterTag.maxPrice);
        return this;
    }

    @Override
    public TutorFilter setMinimumAvgRating(double minimumAvgRating)
    {
        conditions.put(ConditionFilterTag.minReview,
                       t -> tutorProfileMap
                                    .getOrDefault(t, new TutorProfileHandler(t))
                                    .getAvgReview() >= minimumAvgRating);
        return this;
    }

    @Override
    public TutorFilter setMinimumHourlyRate(double desiredHourlyRate)
    {
        conditions.put(ConditionFilterTag.minPrice,
                       t -> t.getHourlyRate() >= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setMaximumHourlyRate(double desiredHourlyRate)
    {
        conditions.put(ConditionFilterTag.maxPrice,
                       t -> t.getHourlyRate() <= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setCourse(String courseString)
    {
        searches.put(SearchFilterTag.course,
                     t -> tutorProfileMap
                             .getOrDefault(t, new TutorProfileHandler(t))
                             .getCourses()
                             .stream()
                             .anyMatch(c -> c
                                                    .getCourseCode()
                                                    .contains(courseString) || c
                                                    .getCourseName()
                                                    .contains(courseString)));
        return this;
    }

    @Override
    public TutorFilter setMajor(String searchString)
    {
        searches.put(SearchFilterTag.major,
                     t -> tutorProfileMap
                             .getOrDefault(t, new TutorProfileHandler(t))
                             .getUserMajor()
                             .contains(searchString));
        return this;
    }

    @Override
    public TutorFilter setName(String searchString)
    {
        searches.put(SearchFilterTag.name,
                     t -> tutorProfileMap
                             .getOrDefault(t, new TutorProfileHandler(t))
                             .getUserName()
                             .contains(searchString));
        return this;
    }

    @Override
    public TutorFilter setLocation(String searchString)
    {
        searches.put(SearchFilterTag.location,
                     t -> tutorProfileMap
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
        Predicate<ITutor> filterConditions = conditions
                .values()
                .stream()
                .reduce(Predicate::and)
                .orElse(t -> true);
        Predicate<ITutor> searchConditions = searches
                .values()
                .stream()
                .reduce(Predicate::or)
                .orElse(t -> true);
        return filterConditions.and(searchConditions);
    }

    private enum SearchFilterTag
    {
        location, major, name, course
    }

    private enum ConditionFilterTag
    {
        minReview, minPrice, maxPrice
    }
}
