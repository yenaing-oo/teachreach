package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentPaymentBinding;

public class PaymentFragment extends Fragment
{
    private BookingViewModel bookingViewModel;

    public PaymentFragment()
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
        return FragmentPaymentBinding
                .inflate(inflater, container, false)
                .getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUpButtons(view);
    }

    private void setUpButtons(View view)
    {
        Button        cancelButton  = view.findViewById(R.id.cancelButton);
        Button        confirmButton = view.findViewById(R.id.confirmButton);
        NavController navController = NavHostFragment.findNavController(this);
        cancelButton.setOnClickListener(v -> navController.navigateUp());
        confirmButton.setOnClickListener(v -> navController.navigateUp());
    }
}
