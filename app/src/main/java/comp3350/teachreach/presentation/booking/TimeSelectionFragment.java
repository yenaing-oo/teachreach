package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.databinding.FragmentTimeSlotSelectionBinding;
import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.profile.TutorProfileViewModel;

public class TimeSelectionFragment extends Fragment
{
    private final View.OnClickListener      listener;
    private       TutorProfileViewModel     tutorProfileViewModel;
    private       TRViewModel               trViewModel;
    private       BookingViewModel          bookingViewModel;
    private       ITutorProfileHandler      profileHandler;
    private       ITutorAvailabilityManager availabilityManager;
    private       ITutor                    tutor;
    private       IAccount                  tutorAccount;
    private       LocalDate                 date;

    public TimeSelectionFragment(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        trViewModel
                              = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);
        tutorProfileViewModel = new ViewModelProvider(requireActivity()).get(
                TutorProfileViewModel.class);
        bookingViewModel      = new ViewModelProvider(requireActivity()).get(
                BookingViewModel.class);

        date = bookingViewModel.getBookingDate().getValue();

        tutor = trViewModel.getTutor().getValue();
        assert tutor != null;

        profileHandler      = new TutorProfileHandler(tutor);
        availabilityManager = new TutorAvailabilityManager();

        tutorAccount = Server
                .getAccountDataAccess()
                .getAccounts()
                .get(tutor.getAccountID());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return FragmentTimeSlotSelectionBinding
                .inflate(inflater, container, false)
                .getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUpTopBar(view);
        setUpTimeSlots(view);
    }

    private void setUpTimeSlots(View v)
    {
        RecyclerView recyclerView = v.findViewById(R.id.timeSlotRecyclerView);

        TimeSlotRecyclerAdapter adapter = new TimeSlotRecyclerAdapter(
                availabilityManager.getAvailabilityAsSlots(tutor, date),
                this::openDetails);

        RecyclerView.LayoutManager lm = new GridLayoutManager(requireContext(),
                                                              2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(lm);
    }

    private void setUpTopBar(View v)
    {
        MaterialToolbar materialToolbar = v.findViewById(R.id.topAppBar);
        materialToolbar.setTitle("Selecting for Date " +
                                 date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        materialToolbar.setNavigationOnClickListener(listener);
    }

    void openDetails(ITimeSlice t)
    {
    }
}
