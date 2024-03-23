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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentTutorProfileSelfViewBinding;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.TRViewModel;

public class TutorProfileSelfViewFragment extends Fragment
{
    ITutorProfileHandler tph;
    private FragmentTutorProfileSelfViewBinding binding;
    private TRViewModel                         vm;
    private TutorProfileViewModel               tvm;
    private IAccount                            account;

    public TutorProfileSelfViewFragment()
    {
    }

    private void fillUpProfileDetails()
    {
        View     v          = binding.getRoot();
        TextView tvName     = v.findViewById(R.id.tvNameField);
        TextView tvPronouns = v.findViewById(R.id.tvPronounsField);
        TextView tvMajor    = v.findViewById(R.id.tvMajorField);

        tvName.setText(account.getUserName());
        tvPronouns.setText(account.getUserPronouns());
        tvMajor.setText(account.getUserMajor());
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
        View   rootView     = binding.getRoot();
        Button btnAddCourse = rootView.findViewById(R.id.btnAddCourse);

        RecyclerView courseRecycler
                = rootView.findViewById(R.id.rvTutoredCourses);

        tvm.setTutoredCoursesCode(tph.getCourseCodeList());

        StringRecyclerAdapter adapter = new StringRecyclerAdapter(tvm
                                                                          .getTutoredCoursesCode()
                                                                          .getValue());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                requireContext());

        courseRecycler.setAdapter(adapter);
        courseRecycler.setLayoutManager(layoutManager);

        tvm
                .getTutoredCoursesCode()
                .observe(getViewLifecycleOwner(), adapter::updateData);

        btnAddCourse.setOnClickListener(v -> {
            DialogueAddCourse addCourse = new DialogueAddCourse();
            addCourse.show(getChildFragmentManager(), "Add Course");
        });
    }

    private void setUpPreferredLocations()
    {
        View   rootView       = binding.getRoot();
        Button btnAddLocation = rootView.findViewById(R.id.btnAddLocation);
        RecyclerView recycler
                = rootView.findViewById(R.id.rvPreferredLocations);

        tvm.setPreferredLocations(tph.getPreferredLocations());

        StringRecyclerAdapter a = new StringRecyclerAdapter(tvm
                                                                    .getPreferredLocations()
                                                                    .getValue());

        RecyclerView.LayoutManager lm
                = new LinearLayoutManager(requireContext());

        recycler.setAdapter(a);
        recycler.setLayoutManager(lm);

        tvm
                .getPreferredLocations()
                .observe(getViewLifecycleOwner(), a::updateData);

        btnAddLocation.setOnClickListener(v -> {
            DialogueAddLocation addLocation = new DialogueAddLocation();
            addLocation.show(getChildFragmentManager(), "Add Location");
        });
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

        vm  = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
        tvm
            = new ViewModelProvider(requireActivity()).get(TutorProfileViewModel.class);

        tph = new TutorProfileHandler(Objects.requireNonNull(vm
                                                                     .getTutor()
                                                                     .getValue()));

        account = vm.getAccount().getValue();
        fillUpProfileDetails();
        setUpEditProfileButton();
        setUpTopBarMenu();
        setUpTutoredCourses();
        setUpPreferredLocations();
        return binding.getRoot();
    }
}