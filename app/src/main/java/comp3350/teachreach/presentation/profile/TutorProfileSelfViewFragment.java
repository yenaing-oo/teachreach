package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;
import java.util.Objects;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentTutorProfileSelfViewBinding;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;

public class TutorProfileSelfViewFragment extends Fragment
{
    private ITutorProfileHandler                profileHandler;
    private FragmentTutorProfileSelfViewBinding binding;
    private TRViewModel                         trViewModel;
    private TutorProfileViewModel               profileViewModel;
    private IAccount                            account;
    private ITutor                              tutor;

    public TutorProfileSelfViewFragment()
    {
    }

    private void fillUpProfileDetails()
    {
        View     v          = binding.getRoot();
        TextView tvName     = v.findViewById(R.id.tvNameField);
        TextView tvPronouns = v.findViewById(R.id.tvPronounsField);
        TextView tvMajor    = v.findViewById(R.id.tvMajorField);
        TextView tvPrice    = v.findViewById(R.id.tvRatingField);
        TextView tvReviews  = v.findViewById(R.id.tvReviewsField);

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

    private void setUpEditProfileButton()
    {
        Button btnEditProfile = binding
                .getRoot()
                .findViewById(R.id.fabEditProfile);
        btnEditProfile.setOnClickListener(view -> NavHostFragment
                .findNavController(this)
                .navigate(R.id.actionToEditTutorProfileFragment));
    }

    private void setUpTopBarMenu()
    {
        MaterialToolbar mtTopBar = binding
                .getRoot()
                .findViewById(R.id.topAppBar);
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

    private void setUpTutoredCourses()
    {
        View   root         = binding.getRoot();
        Button btnAddCourse = root.findViewById(R.id.btnAddCourse);

        RecyclerView recycler = root.findViewById(R.id.rvTutoredCourses);

        profileViewModel.setTutoredCoursesCode(profileHandler.getCourseCodeList());

        StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(
                profileViewModel.getTutoredCoursesCode().getValue());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                requireContext(),
                3);

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

    private void setUpPreferredLocations()
    {
        View         root           = binding.getRoot();
        Button       btnAddLocation = root.findViewById(R.id.btnAddLocation);
        RecyclerView recycler
                                    =
                root.findViewById(R.id.rvPreferredLocations);

        profileViewModel.setPreferredLocations(profileHandler.getPreferredLocations());

        StringRecyclerAdapter recyclerAdapter = new StringRecyclerAdapter(
                profileViewModel.getPreferredLocations().getValue());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                requireContext(),
                3);

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentTutorProfileSelfViewBinding.inflate(inflater,
                                                              container,
                                                              false);

        trViewModel
                         = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(
                TutorProfileViewModel.class);

        profileHandler = new TutorProfileHandler(Objects.requireNonNull(
                trViewModel.getTutor().getValue()));

        account = trViewModel.getAccount().getValue();
        tutor   = trViewModel.getTutor().getValue();

        fillUpProfileDetails();
        setUpEditProfileButton();
        setUpTopBarMenu();
        setUpTutoredCourses();
        setUpPreferredLocations();
        return binding.getRoot();
    }
}