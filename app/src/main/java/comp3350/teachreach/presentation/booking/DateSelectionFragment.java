package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.fragment.app.Fragment;

import comp3350.teachreach.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public
class DateSelectionFragment extends Fragment
{
    private              View                 fragmentView;
    private              OnDateChangeListener dateChangeListener;

    public
    DateSelectionFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DateSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateSelectionFragment newInstance() {
        return new DateSelectionFragment();
    }

    @Override
    public
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public
    View onCreateView(LayoutInflater inflater,
                      ViewGroup container,
                      Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.fragment_date_selection,
                                        container,
                                        false);

        // Inflate the layout for this fragment
        setupCalendar();

        return fragmentView;
    }

    private
    void setupCalendar()
    {
        CalendarView calendarView
                = fragmentView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (dateChangeListener != null) {
                dateChangeListener.onDateSelected(year, month, dayOfMonth);
            }
        });
    }

    public
    void setOnDateChangeListener(OnDateChangeListener listener)
    {
        this.dateChangeListener = listener;
    }
}