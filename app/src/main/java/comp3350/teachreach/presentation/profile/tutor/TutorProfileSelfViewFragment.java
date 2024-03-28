package comp3350.teachreach.presentation.profile.tutor;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.databinding.FragmentTutorProfileSelfViewBinding;
import comp3350.teachreach.logic.interfaces.ISessionHandler;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.session.SessionViewModel;
import comp3350.teachreach.presentation.utils.TimeSliceFormatter;

public
class TutorProfileSelfViewFragment extends Fragment
{
    private static ITutorProfileHandler profileHandler;
    private static ISessionHandler      sessionHandler;
    private static List<String>         prefLocations;
    private static List<String>         tutoredCourses;
    private static List<String>         availStrList;

    private TutorProfileViewModel               profileViewModel;
    private SessionViewModel                    sessionViewModel;
    private FragmentTutorProfileSelfViewBinding binding;
    private IAccount                            account;
    private ITutor                              tutor;
    private boolean                             isLarge, isLandscape;

    public
    TutorProfileSelfViewFragment()
    {
    }

    private
    void fillUpProfileDetails()
    {
        TextView tvName        = binding.tvNameField;
        TextView tvPronouns    = binding.tvPronounsField;
        TextView tvMajor       = binding.tvMajorField;
        TextView tvPrice       = binding.tvRatingField;
        TextView tvReviews     = binding.tvReviewsField;
        TextView tvMoneyEarned = binding.tvMoneyEarnedField;

        tvName.setText(account.getUserName());
        tvPronouns.setText(account.getUserPronouns());
        tvMajor.setText(account.getUserMajor());
        tvPrice.setText(String.format(Locale.getDefault(), "$%.2f/h", tutor.getHourlyRate()));
        tvReviews.setText(String.format(Locale.getDefault(),
                                        "%.1f ⭐(%d)",
                                        profileHandler.getAvgReview(tutor),
                                        tutor.getReviewCount()));
        tvMoneyEarned.setText(String.format(Locale.getDefault(),
                                            "$%.02f",
                                            sessionHandler
                                                    .getAcceptedSessions(tutor)
                                                    .stream()
                                                    .mapToDouble(ISession::getSessionCost)
                                                    .sum()));
    }

    private
    void setUpEditProfileButton()
    {
        Button btnEditProfile = binding.fabEditProfile;
        btnEditProfile.setOnClickListener(v -> NavHostFragment
                .findNavController(this)
                .navigate(R.id.actionToEditTutorProfileFragment));
    }

    private
    void setUpTopBarMenu()
    {
        MaterialToolbar mtTopBar = binding.topAppBar;
        mtTopBar.setOnMenuItemClickListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.tbAccountSettings) {
                NavHostFragment.findNavController(this).navigate(R.id.actionToAccountSettingsFragment);
            }
            return true;
        });
    }

    private
    void setUpTutoredCourses()
    {
        Button            btnAddCourse = binding.btnAddCourse;
        DialogueAddCourse addCourse    = new DialogueAddCourse();
        btnAddCourse.setOnClickListener(v -> addCourse.show(getChildFragmentManager(), "Add Course"));
        RecyclerView               recycler      = binding.rvTutoredCourses;
        int                        spanCount     = isLarge || isLandscape ? 6 : 2;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        recycler.setLayoutManager(layoutManager);
        profileViewModel.setTutoredCoursesCode(profileHandler.getCourseCodeList(tutor));
        tutoredCourses = profileViewModel.getTutoredCoursesCode().getValue();
        StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(tutoredCourses);
        recycler.setAdapter(recyclerAdapter);
        profileViewModel.getTutoredCoursesCode().observe(getViewLifecycleOwner(), recyclerAdapter::updateData);
    }

    private
    void setUpPreferredLocations()
    {
        DialogueAddLocation addLocation    = new DialogueAddLocation();
        Button              btnAddLocation = binding.btnAddLocation;
        btnAddLocation.setOnClickListener(v -> addLocation.show(getChildFragmentManager(), "Add Location"));
        RecyclerView               recycler      = binding.rvPreferredLocations;
        int                        spanCount     = isLarge || isLandscape ? 6 : 2;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new StringRecyclerAdapter(new ArrayList<>()));
        profileViewModel.setPreferredLocations(profileHandler.getPreferredLocations(tutor));
        prefLocations = profileViewModel.getPreferredLocations().getValue();
        StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(prefLocations);
        recycler.setAdapter(recyclerAdapter);
        profileViewModel.getPreferredLocations().observe(getViewLifecycleOwner(), recyclerAdapter::updateData);
    }

    private
    void setUpAvailability()
    {
        DialogueAddLocation addLocation = new DialogueAddLocation();
        Button              btnAddAvail = binding.btnAddAvailability;
        btnAddAvail.setOnClickListener(v -> addLocation.show(getChildFragmentManager(), "Add Availability"));
        RecyclerView               recycler      = binding.rvAvailability;
        int                        spanCount     = isLarge || isLandscape ? 2 : 1;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new StringRecyclerAdapter(new ArrayList<>()));
        availStrList = Server
                .getTutorAvailabilityAccess()
                .getAvailability(tutor)
                .stream()
                .map(TimeSliceFormatter::format)
                .collect(Collectors.toList());
        StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(availStrList);
        recycler.setAdapter(recyclerAdapter);
    }

    @Override
    public
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sessionViewModel = new ViewModelProvider(requireActivity()).get(SessionViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(TutorProfileViewModel.class);
        profileHandler   = profileViewModel.getProfileHandler().getValue();
        sessionHandler   = sessionViewModel.getSessionsAccess().getValue();
    }

    @Override
    public
    View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentTutorProfileSelfViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public
    void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        tutor   = profileViewModel.getTutor().getValue();
        account = profileViewModel.getTutorAccount().getValue();
        Configuration config = getResources().getConfiguration();
        isLarge     = config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE);
        isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE;
        fillUpProfileDetails();
        setUpEditProfileButton();
        setUpTopBarMenu();
        setUpTutoredCourses();
        setUpPreferredLocations();
        setUpAvailability();
    }
}