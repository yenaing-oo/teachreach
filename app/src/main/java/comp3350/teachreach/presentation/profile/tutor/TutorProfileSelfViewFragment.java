package comp3350.teachreach.presentation.profile.tutor;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentTutorProfileSelfViewBinding;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class TutorProfileSelfViewFragment extends Fragment
{
    private TutorProfileViewModel profileViewModel;

    private FragmentTutorProfileSelfViewBinding binding;

    private IAccount             account;
    private ITutor               tutor;
    private ITutorProfileHandler profileHandler;

    private List<String> prefLocations;

    private List<String> tutoredCourses;

    private boolean isLarge, isLandscape;

    public
    TutorProfileSelfViewFragment()
    {
    }

    private
    void fillUpProfileDetails()
    {
        TextView tvName     = binding.tvNameField;
        TextView tvPronouns = binding.tvPronounsField;
        TextView tvMajor    = binding.tvMajorField;
        TextView tvPrice    = binding.tvRatingField;
        TextView tvReviews  = binding.tvReviewsField;

        tvName.setText(account.getUserName());
        tvPronouns.setText(account.getUserPronouns());
        tvMajor.setText(account.getUserMajor());
        tvPrice.setText(String.format(Locale.getDefault(), "$%.2f/h", tutor.getHourlyRate()));
        tvReviews.setText(String.format(Locale.getDefault(),
                                        "%.1f ⭐(%d)",
                                        profileHandler.getAvgReview(tutor),
                                        tutor.getReviewCount()));
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
        Executors.newSingleThreadExecutor().execute(() -> {
            tutoredCourses = profileViewModel.getTutoredCoursesCode().getValue();
            new Handler(Looper.getMainLooper()).post(() -> {
                StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(tutoredCourses);
                recycler.setAdapter(recyclerAdapter);
                profileViewModel.getTutoredCoursesCode().observe(getViewLifecycleOwner(), recyclerAdapter::updateData);
            });
        });
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
        Executors.newSingleThreadExecutor().execute(() -> {
            prefLocations = profileHandler.getPreferredLocations(tutor);
            new Handler(Looper.getMainLooper()).post(() -> {
                StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(prefLocations);
                recycler.setAdapter(recyclerAdapter);
                profileViewModel.getPreferredLocations().observe(getViewLifecycleOwner(), recyclerAdapter::updateData);
            });
        });
    }

    @Override
    public
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(TutorProfileViewModel.class);
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
        tutor          = profileViewModel.getTutor().getValue();
        account        = profileViewModel.getTutorAccount().getValue();
        profileHandler = new TutorProfileHandler();
        Configuration config = getResources().getConfiguration();
        isLarge     = config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE);
        isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE;
        fillUpProfileDetails();
        setUpEditProfileButton();
        setUpTopBarMenu();
        setUpTutoredCourses();
        setUpPreferredLocations();
    }
}