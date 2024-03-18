package comp3350.teachreach.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.application.TRData;
import comp3350.teachreach.logic.account.AuthenticationHandler;
import comp3350.teachreach.logic.account.InputValidator;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TutorHomeActivity;
import comp3350.teachreach.presentation.signup.TutorSignUpActivity;

public
class TutorLoginActivity extends AppCompatActivity
{

    private EditText etTutorEmail, etTutorPassword;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_login);

        etTutorEmail = findViewById(R.id.etTutorEmail);
        etTutorPassword = findViewById(R.id.etTutorPassword);
        Button btnLogin = findViewById(R.id.btnTutorLogin);
        TextView tvSignUp = findViewById(R.id.tvTutorSignUp);

        authenticationHandler = new AuthenticationHandler();

        btnLogin.setOnClickListener(v -> login());

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(TutorLoginActivity.this,
                    TutorSignUpActivity.class);
            startActivity(intent);
        });
    }

    private
    void login()
    {
        String email    = etTutorEmail.getText().toString().trim();
        String password = etTutorPassword.getText().toString().trim();
        try {
            InputValidator.validateEmail(email);
            InputValidator.validatePassword(password);
            ITutor tutor = authenticationHandler.authenticateTutor(email, password);
            TRData.setCurrentTutorID(tutor.getTutorID());

            Intent intent = new Intent(TutorLoginActivity.this,
                    TutorHomeActivity.class);

            startActivity(intent);
            finish();
        } catch (final Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
