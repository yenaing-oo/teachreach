package comp3350.teachreach.logic.interfaces;

import java.util.List;
import java.util.function.Function;

import comp3350.teachreach.objects.interfaces.ITutor;

public interface ITutorFilter
{
    ITutorFilter Reset();

    boolean getMinimumAvgRatingState();

    boolean getMinimumHourlyRateState();

    boolean getMaximumHourlyRateState();

    ITutorFilter clearMinimumAvgRating();

    ITutorFilter clearMinimumHourlyRate();

    ITutorFilter clearMaximumHourlyRate();

    ITutorFilter setMinimumAvgRating(double minimumAvgRating);

    ITutorFilter setMinimumHourlyRate(double desiredHourlyRate);

    ITutorFilter setMaximumHourlyRate(double desiredHourlyRate);

    ITutorFilter setSearchFilter(String searchString);

    Function<List<ITutor>, List<ITutor>> filterFunc();
}
