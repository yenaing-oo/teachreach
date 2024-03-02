package comp3350.teachreach.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.account.CredentialHandler;

public class TutorLoginActivity extends AppCompatActivity {

    private EditText etTutorEmail, etTutorPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private CredentialHandler credentialHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_login);

        etTutorEmail = findViewById(R.id.etTutorEmail);
        etTutorPassword = findViewById(R.id.etTutorPassword);
        btnLogin = findViewById(R.id.btnTutorLogin);
        tvSignUp = findViewById(R.id.tvTutorSignUp);

        credentialHandler = new CredentialHandler();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorLoginActivity.this, TutorSignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String email = etTutorEmail.getText().toString().trim();
        String password = etTutorPassword.getText().toString().trim();

//        if (validateInputs(email, password)) {
        if (credentialHandler.validateCredential(email, password)) {

            Intent intent = new Intent(TutorLoginActivity.this, TutorProfileActivity.class);
            intent.putExtra("TUTOR_EMAIL_KEY", email);
            startActivity(intent);
            finish(); // Close the current activity
        } else {
            Toast.makeText(this, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
//    }

//    private boolean validateInputs(String email, String password) {
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }
}
