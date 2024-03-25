package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentPaymentBinding;
import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.logic.exceptions.payment.ExpiredCardException;
import comp3350.teachreach.logic.exceptions.payment.InvalidCVCException;
import comp3350.teachreach.logic.exceptions.payment.InvalidCardNumberException;
import comp3350.teachreach.logic.exceptions.payment.InvalidExpiryDateException;
import comp3350.teachreach.logic.exceptions.payment.PaymentException;
import comp3350.teachreach.logic.interfaces.ISessionHandler;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.logic.payment.PaymentValidator;
import comp3350.teachreach.logic.session.SessionHandler;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public class PaymentFragment extends Fragment
{
    private Double          grandTotal;
    private TextInputLayout tilCardNumber, tilExpDate, tilCVC;
    private BookingViewModel bookingViewModel;
    private EditText         cardNumber, expDate, cVc;

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
        setUpTextFields(view);
    }

    private void setUpTextFields(View view)
    {
        grandTotal = bookingViewModel.getSessionPrice().getValue();
        TextView tvGrandTotal = view.findViewById(R.id.tvTotalField);
        tvGrandTotal.setText(String.format(Locale.getDefault(),
                                           "$%.2f",
                                           grandTotal));

        tilCardNumber = view.findViewById(R.id.tilCardNumber);
        tilExpDate    = view.findViewById(R.id.tilExpDate);
        tilCVC        = view.findViewById(R.id.tilCVC);

        cardNumber = tilCardNumber.getEditText();
        expDate    = tilExpDate.getEditText();
        cVc        = tilCVC.getEditText();
    }

    private void setUpButtons(View view)
    {
        Button        cancelButton  = view.findViewById(R.id.cancelButton);
        Button        confirmButton = view.findViewById(R.id.confirmButton);
        NavController navController = NavHostFragment.findNavController(this);
        cancelButton.setOnClickListener(v -> navController.navigateUp());
        confirmButton.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking()
    {
        String strCardNumber = cardNumber.getText().toString().trim();
        String strExpDate    = expDate.getText().toString().trim();
        String strCVC        = cVc.getText().toString().trim();
        tilCardNumber.setError(null);
        tilExpDate.setError(null);
        tilCVC.setError(null);
        try {
            PaymentValidator.validatePaymentInfo(strCardNumber,
                                                 strExpDate,
                                                 strCVC);
            ITutorAvailabilityManager availabilityManager
                    = new TutorAvailabilityManager();
            ISessionHandler sessionHandler = new SessionHandler(
                    availabilityManager);
            IStudent student = bookingViewModel.getStudent().getValue();
            ITutor   tutor   = bookingViewModel.getTutor().getValue();
            ITimeSlice sessionTime = bookingViewModel
                    .getSessionTime()
                    .getValue();
            String location = bookingViewModel.getSessionLocation().getValue();
            sessionHandler.bookSession(new Session(student.getStudentID(),
                                                   tutor.getTutorID(),
                                                   sessionTime,
                                                   grandTotal,
                                                   location));
        } catch (InvalidCardNumberException e) {
            tilCardNumber.setError(e.getMessage());
        } catch (InvalidCVCException e) {
            tilCVC.setError(e.getMessage());
        } catch (InvalidExpiryDateException | ExpiredCardException e) {
            tilExpDate.setError(e.getMessage());
        } catch (PaymentException unknownErr) {
            Toast
                    .makeText(requireContext(),
                              "Issue with payment info, please review",
                              Toast.LENGTH_SHORT)
                    .show();
        } catch (final Throwable e) {
            e.printStackTrace();
            Toast
                    .makeText(requireContext(),
                              "Tutor mightn't be available for booking",
                              Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
