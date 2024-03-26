package comp3350.teachreach.presentation.profile.tutor;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentTutorProfileBinding;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.logic.profile.UserProfileFetcher;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.booking.BookingViewModel;
import comp3350.teachreach.presentation.communication.IndividualChat.MessageModel;
import comp3350.teachreach.presentation.utils.TRViewModel;

public
class TutorProfileViewFragment extends Fragment
{
    private static final IUserProfileHandler<ITutor> profileFetcher      = new UserProfileFetcher<>();
    private static final ITutorProfileHandler        profileHandler      = new TutorProfileHandler();
    private static final ITutorAvailabilityManager   availabilityManager = new TutorAvailabilityManager();
    private static final IMessageHandler  messageHandler      = new MessageHandler();
    private              TRViewModel                 trViewModel;
    private              BookingViewModel            bookingViewModel;
    private              FragmentTutorProfileBinding binding;
    private              SlidingPaneLayout           slidingPaneLayout;
    private              ITutor                      tutor;
    private              IStudent                    student;
    private              IAccount                    tutorAccount;
    private              List<String>                prefLocation;


    private MessageModel messageModel;



    private Configuration config;
    private boolean       isLarge, isLandscape;

    public
    TutorProfileViewFragment()
    {
    }

    @Override
    public
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        trViewModel      = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
        bookingViewModel = new ViewModelProvider(requireActivity()).get(BookingViewModel.class);
        messageModel = new ViewModelProvider(requireActivity()).get(
                MessageModel.class);
    }

    @Override
    public
    View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentTutorProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public
    void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        config            = getResources().getConfiguration();
        isLarge           = config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE);
        isLandscape       = config.orientation == Configuration.ORIENTATION_LANDSCAPE;
        slidingPaneLayout = requireActivity().requireViewById(R.id.searchFragment);
        try {
            tutor        = trViewModel.getTutor().getValue();
            student      = trViewModel.getStudent().getValue();
            tutorAccount = profileFetcher.getUserAccount(tutor);
            setUpProfile();
            setUpTopBar();
            setUpTutoredCourses();
            setUpPreferredLocations();
            setUpCalendarView();
            chatGroupIntent(view);
        } catch (final Throwable e) {
            Toast.makeText(requireContext(), "Not so fast!", Toast.LENGTH_SHORT).show();
            slidingPaneLayout.close();
            NavHostFragment.findNavController(this).navigate(R.id.actionToPlaceHolderFragment);
        }
    }


    private
    void setUpTutoredCourses()
    {
        RecyclerView               recyclerView  = binding.rvTutoredCourses;
        int                        spanCount     = isLarge || isLandscape ? 6 : 3;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new StringRecyclerAdapter(new ArrayList<>()));
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<String> coursesCodes = profileHandler.getCourseCodeList(tutor);
                new Handler(Looper.getMainLooper()).post(() -> {
                    StringRecyclerAdapter adapter = new StringRecyclerAdapter(coursesCodes);
                    recyclerView.setAdapter(adapter);
                });
            } catch (final Throwable ignored) {
            }
        });
    }

    private
    void setUpTopBar()
    {
        MaterialToolbar materialToolbar = binding.topAppBar;
        materialToolbar.setTitle(tutorAccount.getUserName());
        materialToolbar.setNavigationOnClickListener(view -> {
            slidingPaneLayout.close();
            NavHostFragment.findNavController(this).navigate(R.id.actionToPlaceHolderFragment);
        });
    }

    private
    void setUpProfile()
    {
        TextView tvPronouns = binding.tvPronounsField;
        TextView tvMajor    = binding.tvMajorField;
        TextView tvPrice    = binding.tvRatingField;
        TextView tvReviews  = binding.tvReviewsField;

        tvPronouns.setText(tutorAccount.getUserPronouns());
        tvMajor.setText(tutorAccount.getUserMajor());
        tvPrice.setText(String.format(Locale.US, "$%.2f/h", tutor.getHourlyRate()));
        tvReviews.setText(String.format(Locale.US,
                                        "%.1f ⭐(%d)",
                                        profileHandler.getAvgReview(tutor),
                                        tutor.getReviewCount()));
    }

    private
    void setUpPreferredLocations()
    {
        RecyclerView               recycler      = binding.rvPreferredLocations;
        int                        spanCount     = isLarge || isLandscape ? 6 : 2;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new StringRecyclerAdapter(new ArrayList<>()));
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                prefLocation = profileHandler.getPreferredLocations(tutor);
                new Handler(Looper.getMainLooper()).post(() -> {
                    StringRecyclerAdapter adapter = new StringRecyclerAdapter(prefLocation);
                    recycler.setAdapter(adapter);
                });
            } catch (final Throwable ignored) {
            }
        });
    }

    private
    void setUpCalendarView()
    {
        CalendarView calendarView = binding.cvCalendarBook;
        Date         date         = Date.from(Instant.now());
        calendarView.setMinDate(date.getTime());
        calendarView.setOnDateChangeListener((v, y, m, d) -> goToDayFragment(y, m, d));
    }

    private
    void goToDayFragment(int y, int m, int d)
    {
        try {
            LocalDate        calDate      = LocalDate.of(y, m + 1, d);
            List<ITimeSlice> slots        = availabilityManager.getAvailabilityAsSlots(tutor, calDate);
            boolean          notAvailable = slots.isEmpty();
            LocalDate        now          = LocalDate.now();
            if (calDate.isBefore(now) || notAvailable) {
                String toastMsg = String.format(Locale.getDefault(),
                                                "%s is not available on %s",
                                                tutorAccount.getUserName(),
                                                calDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
                Toast.makeText(requireContext(), toastMsg, Toast.LENGTH_SHORT).show();
                return;
            }
            bookingViewModel.setBookingDate(calDate);
            bookingViewModel.setTimeSlots(slots);
            bookingViewModel.setTutorAccount(tutorAccount);
            bookingViewModel.setTutor(tutor);
            bookingViewModel.setStudent(student);
            bookingViewModel.setTutorLocations(prefLocation);
            NavHostFragment.findNavController(this).navigate(R.id.actionToTimeSlotSelectionFragment);
        } catch (final Throwable e) {
            Toast.makeText(requireContext(), "Something Happened! Please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void createGroup(ExtendedFloatingActionButton floatingButton)
    {
        int studentID, tutorID;
        studentID = trViewModel.getStudent().getValue().getStudentID();
//                Objects
//                .requireNonNull(trViewModel.getAccount().getValue())
//                .getStudentID();
        tutorID   = trViewModel.getTutor().getValue().getTutorID();
        AccessTutors accessTutors = new AccessTutors();

        AccessAccounts accessAccounts = new AccessAccounts();

        messageModel.setOtherUser( accessAccounts.getAccountByAccountID( accessTutors.getTutorByTutorID(tutorID).getAccountID()).orElse(null));

                //tutorProfileViewModel.getTutor().getValue().getTutorID();
                //this.tutor.getTutorID();

        floatingButton.setError(null);
        try {
            int groupID = messageHandler.createGroup(studentID, tutorID);
            MessageModel messageModel
                    =
                    new ViewModelProvider(requireActivity()).get(MessageModel.class);
            if(groupID>0) {
                messageModel.setGroupID(groupID);
            }

            messageModel.setMessageByID(messageHandler.chatHistoryOfGroupV1(groupID));
            messageModel.setMessageList(messageHandler.retrieveAllMessageByGroupID(groupID));   //try it

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
            //NavHostFragment
            //        .findNavController(requireParentFragment().requireParentFragment())
            //        .navigate(R.id.actionToGroupFragment);
            createGroup(floatingButton);

            NavHostFragment
                    .findNavController(requireParentFragment().requireParentFragment())
                    .navigate(R.id.actionToIndividualChatFragment);
        });
    }
}
