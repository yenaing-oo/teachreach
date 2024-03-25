package comp3350.teachreach.presentation.booking;

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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentReviewBookingBinding;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public class ReviewBookingFragment extends Fragment
{
    private BookingViewModel bookingViewModel;

    public ReviewBookingFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        bookingViewModel = new ViewModelProvider(requireActivity()).get(
                BookingViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return FragmentReviewBookingBinding
                .inflate(inflater, container, false)
                .getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fillUpBookingDetails(view);
        setUpButtons(view);
    }

    private void setUpButtons(View view)
    {
        Button        cancelButton  = view.findViewById(R.id.cancelButton);
        Button        confirmButton = view.findViewById(R.id.confirmButton);
        NavController navController = NavHostFragment.findNavController(this);
        cancelButton.setOnClickListener(v -> navController.navigateUp());
        confirmButton.setOnClickListener(v -> navController.navigate(R.id.actionToPaymentFragment));
    }

    private void fillUpBookingDetails(View view)
    {
        TextView tutorName = view.findViewById(R.id.tvNameField);
        TextView sDate     = view.findViewById(R.id.tvSessionDateField);
        TextView sTime     = view.findViewById(R.id.tvSessionTimeField);
        TextView sDuration = view.findViewById(R.id.tvSessionDurationField);
        TextView sPrice    = view.findViewById(R.id.tvSessionPriceField);
        TextView sLocation = view.findViewById(R.id.tvSessionLocationField);

        ITimeSlice sessionTime = bookingViewModel.getSessionTime().getValue();
        IAccount   account     = bookingViewModel.getTutorAccount().getValue();
        ITutor     tutor       = bookingViewModel.getTutor().getValue();
        String     location    = bookingViewModel
                .getSessionLocation()
                .getValue();
        if (location == null) {
            location = "TBD";
            bookingViewModel.setSessionLocation(location);
        }
        double price = (double) sessionTime.getDuration().toMinutes() / 60 *
                       tutor.getHourlyRate();
        bookingViewModel.setSessionPrice(price);
        assert account != null;
        tutorName.setText(account.getUserName());
        sDate.setText(sessionTime
                              .getStartTime()
                              .format(DateTimeFormatter.ofPattern(
                                      "eee, d MMMM, yyyy")));
        sTime.setText(sessionTime
                              .getStartTime()
                              .format(DateTimeFormatter.ofPattern("h:mm a")));
        sDuration.setText(String.format(Locale.getDefault(),
                                        "%d minutes",
                                        sessionTime.getDuration().toMinutes()));
        sPrice.setText(String.format(Locale.getDefault(), "$%.2f", price));
        sLocation.setText(location);
    }
}
