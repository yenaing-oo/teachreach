package comp3350.teachreach.logic.interfaces;

import java.util.List;
import java.util.function.Function;

import comp3350.teachreach.objects.interfaces.ITutor;

public interface ITutorFilter
{
    ITutorFilter setMinimumHourlyRate(double desiredHourlyRate);

    ITutorFilter setMaximumHourlyRate(double desiredHourlyRate);

    ITutorFilter setCourse(String courseString);

    ITutorFilter setMajor(String searchString);

    ITutorFilter setName(String searchString);

    ITutorFilter setLocation(String searchString);

    Function<List<ITutor>, List<ITutor>> filterFunc();
}
