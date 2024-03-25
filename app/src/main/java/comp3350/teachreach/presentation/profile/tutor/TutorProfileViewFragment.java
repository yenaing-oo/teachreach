package comp3350.teachreach.presentation.profile.tutor;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.databinding.FragmentTutorProfileBinding;
import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.booking.BookingViewModel;
import comp3350.teachreach.presentation.communication.IndividualChat.MessageModel;
import comp3350.teachreach.presentation.utils.TRViewModel;

public class TutorProfileViewFragment extends Fragment
{
    private TutorProfileViewModel tutorProfileViewModel;
    private TRViewModel           trViewModel;
    private BookingViewModel      bookingViewModel;

    private ITutorProfileHandler profileHandler;

    private IMessageHandler           messageHandler;
    private ITutorAvailabilityManager availabilityManager;

    private ITutor   tutor;
    private IStudent student;
    private IAccount tutorAccount;

    private Configuration config;
    private boolean       isLarge, isLandscape;

    public TutorProfileViewFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        trViewModel
                =
                new ViewModelProvider(requireActivity()).get(TRViewModel.class);

        tutorProfileViewModel = new ViewModelProvider(this).get(
                TutorProfileViewModel.class);

        bookingViewModel = new ViewModelProvider(requireActivity()).get(
                BookingViewModel.class);

        tutor   = trViewModel.getTutor().getValue();
        student = trViewModel.getStudent().getValue();
        assert tutor != null;
        assert student != null;

        //vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);

        profileHandler      = new TutorProfileHandler(tutor);
        messageHandler      = new MessageHandler();
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
        return FragmentTutorProfileBinding
                .inflate(inflater, container, false)
                .getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        config      = getResources().getConfiguration();
        isLarge
                    =
                config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE);
        isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE;
        setUpProfile(view);
        setUpTopBar(view);
        setUpTutoredCourses(view);
        setUpPreferredLocations(view);
        setUpCalendarView(view);
        chatGroupIntent(view);
    }

    private void setUpTutoredCourses(View v)
    {
        RecyclerView r = v.findViewById(R.id.rvTutoredCourses);

        tutorProfileViewModel.setTutoredCoursesCode(profileHandler.getCourseCodeList());

        StringRecyclerAdapter a = new StringRecyclerAdapter(
                tutorProfileViewModel.getTutoredCoursesCode().getValue());
        int spanCount = isLarge || isLandscape ? 6 : 3;
        RecyclerView.LayoutManager lm = new GridLayoutManager(requireContext(),
                                                              spanCount);

        r.setAdapter(a);
        r.setLayoutManager(lm);

        tutorProfileViewModel
                .getTutoredCoursesCode()
                .observe(getViewLifecycleOwner(), a::updateData);
    }

    private void setUpTopBar(View v)
    {
        MaterialToolbar materialToolbar = v.findViewById(R.id.topAppBar);
        materialToolbar.setTitle(tutorAccount.getUserName());
        materialToolbar.setNavigationOnClickListener(view -> {
            SlidingPaneLayout slidingPaneLayout
                    = requireActivity().requireViewById(R.id.searchFragment);
            slidingPaneLayout.close();
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.actionToPlaceHolderFragment);
        });
    }

    private void setUpProfile(View v)
    {
        TextView tvPronouns = v.findViewById(R.id.tvPronounsField);
        TextView tvMajor    = v.findViewById(R.id.tvMajorField);
        TextView tvPrice    = v.findViewById(R.id.tvRatingField);
        TextView tvReviews  = v.findViewById(R.id.tvReviewsField);

        tvPronouns.setText(tutorAccount.getUserPronouns());
        tvMajor.setText(tutorAccount.getUserMajor());
        tvPrice.setText(String.format(Locale.US,
                                      "$%.2f/h",
                                      tutor.getHourlyRate()));
        tvReviews.setText(String.format(Locale.US,
                                        "%.1f ⭐(%d)",
                                        profileHandler.getAvgReview(),
                                        profileHandler.getReviewCount()));
    }

    private void setUpPreferredLocations(View v)
    {
        RecyclerView recycler = v.findViewById(R.id.rvPreferredLocations);

        tutorProfileViewModel.setPreferredLocations(profileHandler.getPreferredLocations());

        StringRecyclerAdapter adapter = new StringRecyclerAdapter(
                tutorProfileViewModel.getPreferredLocations().getValue());

        int spanCount = isLarge || isLandscape ? 6 : 2;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                requireContext(),
                spanCount);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);
        tutorProfileViewModel
                .getPreferredLocations()
                .observe(getViewLifecycleOwner(), adapter::updateData);
    }

    private void setUpCalendarView(View view)
    {
        CalendarView calendarView = view.findViewById(R.id.cvCalendarBook);
        Date         date         = Date.from(Instant.now());
        calendarView.setMinDate(date.getTime());
        calendarView.setOnDateChangeListener((v, y, m, d) -> goToDayFragment(y,
                                                                             m,
                                                                             d));
    }

    private void goToDayFragment(int y, int m, int d)
    {
        LocalDate calDate = LocalDate.of(y, m + 1, d);
        List<ITimeSlice> slots = availabilityManager.getAvailabilityAsSlots(
                tutor,
                calDate);
        boolean   notAvailable = slots.isEmpty();
        LocalDate now          = LocalDate.now();
        if (calDate.isBefore(now) || notAvailable) {
            String toastMsg = String.format(Locale.getDefault(),
                                            "%s is not available on %s",
                                            tutorAccount.getUserName(),
                                            calDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            Toast
                    .makeText(requireContext(), toastMsg, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        bookingViewModel.setBookingDate(calDate);
        bookingViewModel.setTimeSlots(slots);
        bookingViewModel.setTutorAccount(tutorAccount);
        bookingViewModel.setTutor(tutor);
        bookingViewModel.setStudent(student);
        bookingViewModel.setTutorLocations(profileHandler.getPreferredLocations());
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.actionToTimeSlotSelectionFragment);
    }

    private void createGroup(ExtendedFloatingActionButton floatingButton)
    {
        int studentID, tutorID;
        studentID = Objects
                .requireNonNull(trViewModel.getAccount().getValue())
                .getStudentID();
        tutorID   = this.tutor.getTutorID();

        floatingButton.setError(null);
        try {
            int groupID = messageHandler.createGroup(studentID, tutorID);
            MessageModel messageModel
                    =
                    new ViewModelProvider(requireActivity()).get(MessageModel.class);
            messageModel.setGroupID(groupID);

            //trViewModel.setUsers(this.tutorAccount);
        } catch (final Exception e) {
            floatingButton.setError(e.getMessage());
            Log.e("GroupCreation", "Error creating group: " + e.getMessage());
            Toast
                    .makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG)
                    .show(); //i wanna try?
            Snackbar
                    .make(floatingButton, e.getMessage(), Snackbar.LENGTH_LONG)
                    .show();
        }
    }
    //question!: WIll IT error if i open chat button again?

    private void chatGroupIntent(View v)
    {
        ExtendedFloatingActionButton floatingButton
                = v.findViewById(R.id.fabMsg);

        floatingButton.setOnClickListener(view -> {
            createGroup(floatingButton);
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.actionToIndividualChatFragment);
        });
    }
}
