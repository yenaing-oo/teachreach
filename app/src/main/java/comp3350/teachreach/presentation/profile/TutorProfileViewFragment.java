package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.utils.TutorProfileFormatter;

public class TutorProfileViewFragment extends Fragment
{
    private TRViewModel vm;
    private View        view;

    public TutorProfileViewFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tutor_profile,
                                container,
                                false);
        setUpProfile(view);
        return view;
    }

    private void setUpProfile(View v)
    {
        TextView tvPrice  = v.findViewById(R.id.tvRating);
        TextView tvRating = v.findViewById(R.id.tvReviews);
        ITutorProfileHandler tutorProfile = new TutorProfileHandler(vm
                                                                            .getTutorId()
                                                                            .getValue());
        TutorProfileFormatter tutorProfileFormatter = new TutorProfileFormatter(
                tutorProfile);

        tvPrice.setText(String.valueOf(tutorProfileFormatter.getHourlyRate()));
        tvRating.setText(String.valueOf(tutorProfileFormatter.getRating()));
    }
}
