package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import comp3350.teachreach.R;
import java.util.Calendar;

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
        if (!validateCard()) {
            Toast.makeText(this, "Please check the card details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

        }
    }
// will be removed once payment logic is implemented Validator needs to be in LOGIC
    //**
// NEED TO BE REAPPLIED FROM THIS PART ONCE LOGIC IMPLEMENDTED
    //**
    private boolean validateCard() {
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String expDate = expDateEditText.getText().toString().trim();
        String cvc = cvcEditText.getText().toString().trim();

        if (cardNumber.length() != 16) {
            Toast.makeText(this, "Invalid card number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cvc.length() != 3) {
            Toast.makeText(this, "Invalid CVC", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidExpDate(expDate)) {
            Toast.makeText(this, "Invalid expiry date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidExpDate(String expDate) {
        String[] parts = expDate.split("/");
        if (parts.length != 2) return false;

        try {
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            if (month < 1 || month > 12) return false;

            Calendar now = Calendar.getInstance();
            int currentYear = now.get(Calendar.YEAR) % 100;
            int currentMonth = now.get(Calendar.MONTH) + 1;

            if (year < currentYear || (year == currentYear && month < currentMonth)) {
                return false;
            }

        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
