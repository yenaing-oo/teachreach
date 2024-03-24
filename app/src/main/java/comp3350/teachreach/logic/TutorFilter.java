package comp3350.teachreach.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import comp3350.teachreach.logic.interfaces.ITutorFilter;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorFilter implements ITutorFilter
{
    Map<ConditionFilterTag, Predicate<ITutor>> conditions = new HashMap<>();

    Predicate<ITutor> searchCondition = t -> true;

    public TutorFilter()
    {
    }

    public static TutorFilter New()
    {
        return new TutorFilter();
    }

    @Override
    public TutorFilter Reset()
    {
        conditions.clear();
        searchCondition = t -> true;
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
                       t -> new TutorProfileHandler(t).getAvgReview() >=
                            minimumAvgRating);
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
    public TutorFilter setSearchFilter(String searchString)
    {
        final String s = searchString.trim().toLowerCase();
        searchCondition = t -> {
            ITutorProfileHandler tph = new TutorProfileHandler(t);
            return tph
                           .getCourses()
                           .stream()
                           .anyMatch(c -> c
                                                  .getCourseCode()
                                                  .toLowerCase()
                                                  .contains(s) || c
                                                  .getCourseName()
                                                  .toLowerCase()
                                                  .contains(s)) || tph
                           .getPreferredLocations()
                           .stream()
                           .map(String::toLowerCase)
                           .anyMatch(str -> str.contains(s)) ||
                   tph.getUserName().toLowerCase().contains(s) ||
                   tph.getUserMajor().toLowerCase().contains(s);
        };
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
        return filterConditions.and(searchCondition);
    }

    private enum ConditionFilterTag
    {
        minReview, minPrice, maxPrice
    }
}
