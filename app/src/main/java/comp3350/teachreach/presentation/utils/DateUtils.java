package comp3350.teachreach.presentation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public
class DateUtils
{
    public static
    String formatDate(int year, int month, int dayOfMonth)
    {
        // Construct a date representation from the selected year, month, and
        // day
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);

        return dateFormatHelper(selectedDate);
    }

    private static
    String dateFormatHelper(Calendar date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());
        return sdf.format(date.getTime());
    }
}
