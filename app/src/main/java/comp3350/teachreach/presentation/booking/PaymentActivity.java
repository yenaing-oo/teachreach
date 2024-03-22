package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.exceptions.payment.ExpiredCardException;
import comp3350.teachreach.logic.exceptions.payment.InvalidCVCException;
import comp3350.teachreach.logic.exceptions.payment.InvalidCardNumberException;
import comp3350.teachreach.logic.exceptions.payment.InvalidExpiryDateException;
import comp3350.teachreach.logic.exceptions.payment.PaymentException;
import comp3350.teachreach.logic.payment.PaymentValidator;


public class PaymentActivity extends AppCompatActivity {

    private EditText cardNumberEditText;
    private EditText expDateEditText;
    private EditText cvcEditText;
    private Button confirmButton;
    private Button cancelButton;
    private TextView amountTextView;
    private TextView instructionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        expDateEditText = findViewById(R.id.expDateEditText);
        cvcEditText = findViewById(R.id.cvcEditText);
        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);
        amountTextView = findViewById(R.id.paymentAmountTextView);
        instructionTextView = findViewById(R.id.instructionTextView);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPayment();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPayment();
            }
        });
    }

    private void cancelPayment() {
        // to previous page
        finish();
    }

    private void confirmPayment() {
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String expDate = expDateEditText.getText().toString().trim();
        String cvc = cvcEditText.getText().toString().trim();
        boolean valid = false;

        try {
            valid = PaymentValidator.validatePaymentInfo(cardNumber, expDate, cvc);
        } catch (InvalidCardNumberException | InvalidCVCException | InvalidExpiryDateException |
                 ExpiredCardException cardErr) {
            Toast.makeText(this, cardErr.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (PaymentException unknownErr) {
            Toast.makeText(this, "Issue with payment info, please review", Toast.LENGTH_SHORT).show();
        }

        if(valid) {
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        }
    }

}
