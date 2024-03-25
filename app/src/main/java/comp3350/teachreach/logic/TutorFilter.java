package comp3350.teachreach.logic;

import java.util.ArrayList;
import java.util.Comparator;
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
    Map<ITutor, ITutorProfileHandler> tphMap = new HashMap<>();

    Map<ConditionFilterTag, Predicate<ITutor>> cond = new HashMap<>();

    List<SortConditionTag> sortCondSet = new ArrayList<>();

    Predicate<ITutor> searchCondition = t -> true;

    private TutorFilter()
    {
    }

    public static TutorFilter New()
    {
        return new TutorFilter();
    }

    @Override
    public TutorFilter Reset()
    {
        cond.clear();
        sortCondSet.clear();
        searchCondition = t -> true;
        return this;
    }

    @Override
    public TutorFilter resetSearchString()
    {
        searchCondition = t -> true;
        return this;
    }

    @Override
    public boolean getMinimumAvgRatingState()
    {
        return cond.containsKey(ConditionFilterTag.minReview);
    }

    @Override
    public boolean getMinimumHourlyRateState()
    {
        return cond.containsKey(ConditionFilterTag.minPrice);
    }

    @Override
    public boolean getMaximumHourlyRateState()
    {
        return cond.containsKey(ConditionFilterTag.maxPrice);
    }

    @Override
    public boolean getCourseCodeState()
    {
        return cond.containsKey(ConditionFilterTag.courseCode);
    }

    @Override
    public boolean getSortByPriceState()
    {
        return sortCondSet.contains(SortConditionTag.byPrice);
    }

    @Override
    public boolean getSortByReviewsState()
    {
        return sortCondSet.contains(SortConditionTag.byReviews);
    }

    @Override
    public TutorFilter clearMinimumAvgRating()
    {
        cond.remove(ConditionFilterTag.minReview);
        return this;
    }

    @Override
    public TutorFilter clearMinimumHourlyRate()
    {
        cond.remove(ConditionFilterTag.minPrice);
        return this;
    }

    @Override
    public TutorFilter clearMaximumHourlyRate()
    {
        cond.remove(ConditionFilterTag.maxPrice);
        return this;
    }

    @Override
    public TutorFilter clearCourseCode()
    {
        cond.remove(ConditionFilterTag.courseCode);
        return this;
    }

    @Override
    public TutorFilter setMinimumAvgRating(double minimumAvgRating)
    {
        cond.put(ConditionFilterTag.minReview,
                 t -> tphMap
                              .computeIfAbsent(t, TutorProfileHandler::new)
                              .getAvgReview() >= minimumAvgRating);
        return this;
    }

    @Override
    public TutorFilter setMinimumHourlyRate(double desiredHourlyRate)
    {
        cond.put(ConditionFilterTag.minPrice,
                 t -> t.getHourlyRate() >= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setMaximumHourlyRate(double desiredHourlyRate)
    {
        cond.put(ConditionFilterTag.maxPrice,
                 t -> t.getHourlyRate() <= desiredHourlyRate);
        return this;
    }

    @Override
    public TutorFilter setCourseCode(String courseCode)
    {
        cond.put(ConditionFilterTag.courseCode,
                 t -> tphMap
                         .computeIfAbsent(t, TutorProfileHandler::new)
                         .getCourseCodeList()
                         .contains(courseCode));
        return this;
    }

    @Override
    public TutorFilter setSearchFilter(String searchString)
    {
        final String s = searchString.trim().toLowerCase();
        searchCondition = t -> {
            ITutorProfileHandler tph = tphMap.computeIfAbsent(t,
                                                              TutorProfileHandler::new);
            return tph
                           .getCourseCodeList()
                           .stream()
                           .anyMatch(code -> code.toLowerCase().contains(s))

                   || tph
                           .getCourseDescriptionList()
                           .stream()
                           .anyMatch(desc -> desc.toLowerCase().contains(s))

                   || tph
                           .getPreferredLocations()
                           .stream()
                           .anyMatch(l -> l.toLowerCase().contains(s))

                   || tph.getUserName().toLowerCase().contains(s)

                   || tph.getUserMajor().toLowerCase().contains(s);
        };
        return this;
    }

    @Override
    public TutorFilter setSortByPrice()
    {
        if (sortCondSet.contains(SortConditionTag.byPrice)) {
            return this;
        }
        sortCondSet.add(SortConditionTag.byPrice);
        return this;
    }

    @Override
    public TutorFilter setSortByReviews()
    {
        if (sortCondSet.contains(SortConditionTag.byReviews)) {
            return this;
        }
        sortCondSet.add(SortConditionTag.byReviews);
        return this;
    }

    @Override
    public TutorFilter clearSortByPrice()
    {
        sortCondSet.remove(SortConditionTag.byPrice);
        return this;
    }

    @Override
    public TutorFilter clearSortByReviews()
    {
        sortCondSet.remove(SortConditionTag.byReviews);
        return this;
    }

    @Override
    public Function<List<ITutor>, List<ITutor>> filterFunc()
    {
        return t -> t
                .stream()
                .filter(condition())
                .sorted(sortBy())
                .collect(Collectors.toList());
    }

    @Override
    public Function<List<ITutor>, List<ITutor>> filterFunc(Predicate<ITutor> p)
    {
        return t -> t
                .stream()
                .filter(p)
                .sorted(sortBy())
                .collect(Collectors.toList());
    }

    private Predicate<ITutor> condition()
    {
        Predicate<ITutor> filterConditions = cond
                .values()
                .stream()
                .reduce(Predicate::and)
                .orElse(t -> true);
        return filterConditions.and(searchCondition);
    }

    private Comparator<ITutor> sortBy()
    {
        Comparator<ITutor> result = null;

        Comparator<ITutor> byPrice
                = Comparator.comparingDouble(ITutor::getHourlyRate);

        Comparator<ITutor> byReviews
                = Comparator.comparingDouble(tutor -> tphMap
                .computeIfAbsent(tutor, TutorProfileHandler::new)
                .getAvgReview());
        byReviews = byReviews.reversed();

        for (SortConditionTag t : sortCondSet) {
            switch (t) {
                case byPrice -> result = (result == null) ?
                                         byPrice :
                                         result.thenComparing(byPrice);
                case byReviews -> result = (result == null) ?
                                           byReviews :
                                           result.thenComparing(byReviews);
            }
        }

        return result == null ?
               Comparator.comparingInt(ITutor::getTutorID) :
               result;
    }

    private enum ConditionFilterTag
    {
        minReview, minPrice, maxPrice, courseCode
    }

    private enum SortConditionTag
    {
        byPrice, byReviews
    }
}
