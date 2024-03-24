package comp3350.teachreach.presentation.booking;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentTimeSlotSelectionBinding;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.presentation.utils.GridSpacingItemDecoration;

public class TimeSelectionFragment extends Fragment
{
    private final View.OnClickListener back;
    private       BookingViewModel     bookingViewModel;
    private       LocalDate            date;

    public TimeSelectionFragment(View.OnClickListener back)
    {
        this.back = back;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        bookingViewModel = new ViewModelProvider(requireActivity()).get(
                BookingViewModel.class);
        date             = bookingViewModel.getBookingDate().getValue();
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
                bookingViewModel.getTimeSlots().getValue(),
                this::openDetails);

        Configuration conf = getResources().getConfiguration();

        int spanCount
                =
                conf.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE) ?
                  4 :
                  2;

        RecyclerView.LayoutManager lm = new GridLayoutManager(requireContext(),
                                                              spanCount);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(lm);

        int spacingInPixels
                =
                getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,
                                                                     spacingInPixels,
                                                                     true,
                                                                     0));
    }

    private void setUpTopBar(View v)
    {
        MaterialToolbar materialToolbar = v.findViewById(R.id.topAppBar);
        materialToolbar.setTitle("Booking for " +
                                 date.format(DateTimeFormatter.ofPattern(
                                         "eee, d MMM, yyyy")));
        materialToolbar.setNavigationOnClickListener(back);
    }

    void openDetails(ITimeSlice t)
    {
        bookingViewModel.setSessionTime(t);
        FragmentManager fm = getParentFragmentManager();
        fm
                .beginTransaction()
                .replace(R.id.rightSide, new ReviewBookingFragment(back, back))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
