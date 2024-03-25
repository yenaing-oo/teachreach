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

import java.util.Locale;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentTutorProfileSelfViewBinding;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorProfileSelfViewFragment extends Fragment
{
    private TutorProfileViewModel profileViewModel;

    private IAccount             account;
    private ITutor               tutor;
    private ITutorProfileHandler profileHandler;

    private boolean isLarge, isLandscape;

    public TutorProfileSelfViewFragment()
    {
    }

    private void fillUpProfileDetails(View view)
    {
        TextView tvName     = view.findViewById(R.id.tvNameField);
        TextView tvPronouns = view.findViewById(R.id.tvPronounsField);
        TextView tvMajor    = view.findViewById(R.id.tvMajorField);
        TextView tvPrice    = view.findViewById(R.id.tvRatingField);
        TextView tvReviews  = view.findViewById(R.id.tvReviewsField);

        tvName.setText(account.getUserName());
        tvPronouns.setText(account.getUserPronouns());
        tvMajor.setText(account.getUserMajor());
        tvPrice.setText(String.format(Locale.getDefault(),
                                      "$%.2f/h",
                                      tutor.getHourlyRate()));
        tvReviews.setText(String.format(Locale.getDefault(),
                                        "%.1f ⭐(%d)",
                                        profileHandler.getAvgReview(),
                                        profileHandler.getReviewCount()));
    }

    private void setUpEditProfileButton(View view)
    {
        Button btnEditProfile = view.findViewById(R.id.fabEditProfile);
        btnEditProfile.setOnClickListener(v -> NavHostFragment
                .findNavController(this)
                .navigate(R.id.actionToEditTutorProfileFragment));
    }

    private void setUpTopBarMenu(View view)
    {
        MaterialToolbar mtTopBar = view.findViewById(R.id.topAppBar);
        mtTopBar.setOnMenuItemClickListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.tbAccountSettings) {
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.actionToAccountSettingsFragment);
            }
            return true;
        });
    }

    private void setUpTutoredCourses(View view)
    {
        Button btnAddCourse = view.findViewById(R.id.btnAddCourse);

        RecyclerView recycler = view.findViewById(R.id.rvTutoredCourses);

        profileViewModel.setTutoredCoursesCode(profileHandler.getCourseCodeList());

        StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(
                profileViewModel.getTutoredCoursesCode().getValue());

        int spanCount = isLarge || isLandscape ? 6 : 2;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                requireContext(),
                spanCount);

        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(layoutManager);

        profileViewModel
                .getTutoredCoursesCode()
                .observe(getViewLifecycleOwner(), recyclerAdapter::updateData);

        DialogueAddCourse addCourse = new DialogueAddCourse();
        btnAddCourse.setOnClickListener(v -> addCourse.show(
                getChildFragmentManager(),
                "Add Course"));
    }

    private void setUpPreferredLocations(View view)
    {
        Button       btnAddLocation = view.findViewById(R.id.btnAddLocation);
        RecyclerView recycler
                                    =
                view.findViewById(R.id.rvPreferredLocations);

        profileViewModel.setPreferredLocations(profileHandler.getPreferredLocations());

        StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(
                profileViewModel.getPreferredLocations().getValue());

        int spanCount = isLarge || isLandscape ? 6 : 2;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                requireContext(),
                spanCount);

        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(layoutManager);

        profileViewModel
                .getPreferredLocations()
                .observe(getViewLifecycleOwner(), recyclerAdapter::updateData);

        DialogueAddLocation addLocation = new DialogueAddLocation();
        btnAddLocation.setOnClickListener(v -> addLocation.show(
                getChildFragmentManager(),
                "Add Location"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(
                TutorProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return FragmentTutorProfileSelfViewBinding
                .inflate(inflater, container, false)
                .getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        tutor          = profileViewModel.getTutor().getValue();
        account        = profileViewModel.getTutorAccount().getValue();
        profileHandler = new TutorProfileHandler(tutor);
        Configuration config = getResources().getConfiguration();
        isLarge
                    =
                config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE);
        isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE;
        fillUpProfileDetails(view);
        setUpEditProfileButton(view);
        setUpTopBarMenu(view);
        setUpTutoredCourses(view);
        setUpPreferredLocations(view);
    }
}