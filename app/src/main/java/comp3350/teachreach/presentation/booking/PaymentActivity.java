package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.exceptions.payment.ExpiredCardException;
import comp3350.teachreach.logic.exceptions.payment.InvalidCVCException;
import comp3350.teachreach.logic.exceptions.payment.InvalidCardNumberException;
import comp3350.teachreach.logic.exceptions.payment.InvalidExpiryDateException;
import comp3350.teachreach.logic.exceptions.payment.PaymentException;
import comp3350.teachreach.logic.payment.PaymentValidator;

public class PaymentActivity extends AppCompatActivity
{

    private EditText cardNumberEditText;
    private EditText expDateEditText;
    private EditText cvcEditText;
    private Button   confirmButton;
    private Button   cancelButton;
    private TextView amountTextView;
    private TextView instructionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardNumberEditText  = findViewById(R.id.cardNumberEditText);
        expDateEditText     = findViewById(R.id.expDateEditText);
        cvcEditText         = findViewById(R.id.cvcEditText);
        confirmButton       = findViewById(R.id.confirmButton);
        cancelButton        = findViewById(R.id.cancelButton);
        amountTextView      = findViewById(R.id.paymentAmountTextView);
        instructionTextView = findViewById(R.id.instructionTextView);

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmPayment();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cancelPayment();
            }
        });
    }

    private void cancelPayment()
    {
        // to previous page
        finish();
    }

    private void confirmPayment()
    {
        String  cardNumber = cardNumberEditText.getText().toString().trim();
        String  expDate    = expDateEditText.getText().toString().trim();
        String  cvc        = cvcEditText.getText().toString().trim();
        boolean valid      = false;

        try {
            valid = PaymentValidator.validatePaymentInfo(cardNumber,
                                                         expDate,
                                                         cvc);
        } catch (InvalidCardNumberException cardErr) {
            Toast
                    .makeText(this, cardErr.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        } catch (InvalidCVCException cvcErr) {
            Toast
                    .makeText(this, cvcErr.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        } catch (InvalidExpiryDateException expErr1) {
            Toast
                    .makeText(this, expErr1.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        } catch (ExpiredCardException expErr2) {
            Toast
                    .makeText(this, expErr2.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        } catch (PaymentException unknownErr) {
            Toast
                    .makeText(this,
                              "Issue with payment info, please review",
                              Toast.LENGTH_SHORT)
                    .show();
        }

        if (valid) {
            Toast
                    .makeText(this, "Payment successful", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private boolean isValidExpDate(String expDate)
    {
        String[] parts = expDate.split("/");
        if (parts.length != 2) {
            return false;
        }

        try {
            int month = Integer.parseInt(parts[0]);
            int year  = Integer.parseInt(parts[1]);

            if (month < 1 || month > 12) {
                return false;
            }

            Calendar now          = Calendar.getInstance();
            int      currentYear  = now.get(Calendar.YEAR) % 100;
            int      currentMonth = now.get(Calendar.MONTH) + 1;

            if (year < currentYear ||
                (year == currentYear && month < currentMonth)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
