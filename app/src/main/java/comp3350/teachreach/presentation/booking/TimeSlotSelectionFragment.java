package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import comp3350.teachreach.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeSlotSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeSlotSelectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    public TimeSlotSelectionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TimeSlotSelectionFragment newInstance() {
        TimeSlotSelectionFragment fragment = new TimeSlotSelectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_slot_selection, container, false);
        return view;
    }
}