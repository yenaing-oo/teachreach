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

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.utils.GridSpacingItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeSlotSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeSlotSelectionFragment extends Fragment implements ITimeSlotRecyclerView {
    private TimeSlotRecyclerViewAdapter timeSlotRecyclerViewAdapter;
    private static final String ARG_ID = "TUTOR_ID";
    private OnTimeSlotSelectedListener timeSlotSelectedListener;
    private List<ITimeSlice> timeSlots;
    private int tutorID;
    private TutorAvailabilityManager tutorAvailabilityManager;
    private AccessTutors accessTutors;

    public TimeSlotSelectionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TimeSlotSelectionFragment newInstance(int tutorID) {
        TimeSlotSelectionFragment fragment = new TimeSlotSelectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, tutorID);
        fragment.setArguments(args);
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
        tutorAvailabilityManager = new TutorAvailabilityManager();
        accessTutors = new AccessTutors();
        Bundle args = getArguments();
        if (args != null) {
            // Extract the ID from arguments
            this.tutorID = args.getInt(ARG_ID, -1); // -1 is the default value if the argument is not found
        }
        ITutor tutor = accessTutors.getTutorByTutorID(this.tutorID);
        this.timeSlots = tutorAvailabilityManager.getAvailabilityAsSlots(tutor);
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

        timeSlotRecyclerViewAdapter = new TimeSlotRecyclerViewAdapter(getContext(), timeSlots, this);
        recyclerView.setAdapter(timeSlotRecyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
    }

    @Override
    public void onTimeSlotItemClick(int position) {
        ITimeSlice selectedTimeSlot = timeSlots.get(position);
        timeSlotSelectedListener.onTimeSlotSelected(selectedTimeSlot);
    }

}