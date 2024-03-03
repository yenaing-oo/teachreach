package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import comp3350.teachreach.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateSelectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EMAIL = "EMAIL";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragmentView;
    private FragmentManager fragmentManager;

    public DateSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email Tutor email
     * @return A new instance of fragment DateSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateSelectionFragment newInstance(String email) {
        DateSelectionFragment fragment = new DateSelectionFragment();
        Bundle args = new Bundle();
        args.putString(EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = requireActivity().getSupportFragmentManager();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(EMAIL);
            // fetch available dates for tutor associated with email
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_date_selection, container, false);
        ;
        // Inflate the layout for this fragment
        setupCalendar();

        return fragmentView;
    }

    private void addTimeSlotSelectionFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragmentView, TimeSlotSelectionFragment.newInstance(), "fragment")
                .addToBackStack(null)
                .commit();
    }

    private void setupCalendar() {
        CalendarView calendarView = fragmentView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                addTimeSlotSelectionFragment();
            }
        });
    }

}