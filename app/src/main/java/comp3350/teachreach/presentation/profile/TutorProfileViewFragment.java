package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.databinding.FragmentTutorProfileBinding;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.utils.TutorProfileFormatter;

public class TutorProfileViewFragment extends Fragment
{
    private final View.OnClickListener listener;
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
        TRViewModel vm = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);
        tutor        = vm.getTutor().getValue();
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
        return v;
    }

    private void setUpTopBar(View v)
    {
        MaterialToolbar materialToolbar = v.findViewById(R.id.topAppBar);
        materialToolbar.setTitle(tutorAccount.getUserName());
        materialToolbar.setNavigationOnClickListener(listener);
    }

    private void setUpProfile(View v)
    {
        TextView             tvPrice      = v.findViewById(R.id.tvRating);
        TextView             tvRating     = v.findViewById(R.id.tvReviews);
        ITutorProfileHandler tutorProfile = new TutorProfileHandler(tutor);
        TutorProfileFormatter tutorProfileFormatter = new TutorProfileFormatter(
                tutorProfile);

        tvPrice.setText(String.valueOf(tutorProfileFormatter.getHourlyRate()));
        tvRating.setText(String.valueOf(tutorProfileFormatter.getRating()));
    }
}
