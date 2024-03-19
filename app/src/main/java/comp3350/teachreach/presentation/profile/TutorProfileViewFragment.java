package comp3350.teachreach.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.booking.BookingActivity;
import comp3350.teachreach.presentation.utils.TutorProfileFormatter;

public class TutorProfileViewFragment extends Fragment
{
    private TRViewModel vm;
    private View        view;

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
        view = inflater.inflate(R.layout.activity_tutor_profile,
                                container,
                                false);
        setUpProfile(view);
        return view;
    }

    private void setUpProfile(View v)
    {
        TextView tvCourses      = v.findViewById(R.id.tvCourses);
        TextView tvPrice        = v.findViewById(R.id.tvPrice);
        TextView tvRating       = v.findViewById(R.id.tvRating);
        Button   btnBookSession = v.findViewById(R.id.btnBookSession);
        ITutorProfileHandler tutorProfile
                = new TutorProfileHandler(vm.getTutorId().getValue());
        TutorProfileFormatter tutorProfileFormatter = new TutorProfileFormatter(
                tutorProfile);

        tvCourses.setText(tutorProfileFormatter.getCourses());
        tvPrice.setText(String.valueOf(tutorProfileFormatter.getHourlyRate()));
        tvRating.setText(String.valueOf(tutorProfileFormatter.getRating()));

        btnBookSession.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), BookingActivity.class);
            startActivity(intent);
        });
    }
}
