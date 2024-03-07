package comp3350.teachreach.presentation.booking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.presentation.utils.GridSpacingItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeSlotSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeSlotSelectionFragment extends Fragment implements ITimeSlotRecyclerView {
    private TimeSlotRecyclerViewAdapter timeSlotRecyclerViewAdapter;
    private List<String> timeSlotList;
    private OnTimeSlotSelectedListener timeSlotSelectedListener;

    public TimeSlotSelectionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TimeSlotSelectionFragment newInstance() {
        TimeSlotSelectionFragment fragment = new TimeSlotSelectionFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnTimeSlotSelectedListener) {
            timeSlotSelectedListener = (OnTimeSlotSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimeSlotSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeSlotList = new ArrayList<>();
        timeSlotList.add("10:30 AM");
        timeSlotList.add("11:00 AM");
        timeSlotList.add("11:30 AM");
        timeSlotList.add("12:00 PM");
        timeSlotList.add("12:30 PM");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_slot_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.timeSlotRecyclerView);

        timeSlotRecyclerViewAdapter = new TimeSlotRecyclerViewAdapter(getContext(), timeSlotList, this);
        recyclerView.setAdapter(timeSlotRecyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
    }

    @Override
    public void onTimeSlotItemClick(int position) {
        // Retrieve the clicked time slot
        String selectedTimeSlot = timeSlotList.get(position);
        // Pass the time slot back to the activity
        timeSlotSelectedListener.onTimeSlotSelected(selectedTimeSlot);
    }

}