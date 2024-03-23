package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.databinding.FragmentTutorProfileBinding;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;

public class TutorProfileViewFragment extends Fragment
{
    private final View.OnClickListener listener;

    private TutorProfileViewModel tvm;
    private TRViewModel           vm;

    private ITutorProfileHandler tph;

    private ITutor   tutor;
    private IAccount tutorAccount;

    public TutorProfileViewFragment(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        vm  = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
        tvm
            = new ViewModelProvider(requireActivity()).get(TutorProfileViewModel.class);

        tutor = vm.getTutor().getValue();
        assert tutor != null;
        tph          = new TutorProfileHandler(tutor);
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
        FragmentTutorProfileBinding binding
                = FragmentTutorProfileBinding.inflate(inflater,
                                                      container,
                                                      false);
        View v = binding.getRoot();
        setUpProfile(v);
        setUpTopBar(v);
        setUpTutoredCourses(v);
        setUpPreferredLocations(v);
        return v;
    }

    private void setUpTutoredCourses(View v)
    {
        RecyclerView r = v.findViewById(R.id.rvTutoredCourses);

        tvm.setTutoredCoursesCode(tph.getCourseCodeList());

        StringRecyclerAdapter a = new StringRecyclerAdapter(tvm
                                                                    .getTutoredCoursesCode()
                                                                    .getValue());

        RecyclerView.LayoutManager lm = new GridLayoutManager(requireContext(),
                                                              3);

        r.setAdapter(a);
        r.setLayoutManager(lm);

        tvm
                .getTutoredCoursesCode()
                .observe(getViewLifecycleOwner(), a::updateData);
    }

    private void setUpTopBar(View v)
    {
        MaterialToolbar materialToolbar = v.findViewById(R.id.topAppBar);
        materialToolbar.setTitle(tutorAccount.getUserName());
        materialToolbar.setNavigationOnClickListener(listener);
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
                                        tph.getAvgReview(),
                                        tph.getReviewCount()));
    }

    private void setUpPreferredLocations(View v)
    {
        RecyclerView r = v.findViewById(R.id.rvPreferredLocations);

        tvm.setPreferredLocations(tph.getPreferredLocations());

        StringRecyclerAdapter a = new StringRecyclerAdapter(tvm
                                                                    .getPreferredLocations()
                                                                    .getValue());

        RecyclerView.LayoutManager lm = new GridLayoutManager(requireContext(),
                                                              3);

        r.setAdapter(a);
        r.setLayoutManager(lm);

        tvm
                .getPreferredLocations()
                .observe(getViewLifecycleOwner(), a::updateData);
    }
}
