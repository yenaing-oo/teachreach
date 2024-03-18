package comp3350.teachreach.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import comp3350.teachreach.R;
import comp3350.teachreach.application.TRData;
import comp3350.teachreach.logic.account.AuthenticationHandler;
import comp3350.teachreach.logic.account.InputValidator;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TutorHomeActivity;
import comp3350.teachreach.presentation.signup.TutorSignUpActivity;

public class TutorLoginActivity extends AppCompatActivity
{
    private TextInputLayout tilTutorEmail, tilTutorPassword;
    private EditText etTutorEmail, etTutorPassword;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_login);

        Button          btnLogin  = findViewById(R.id.btnLoginAsTutor);
        Button          btnSignUp = findViewById(R.id.btnSignupAsTutor);
        MaterialToolbar mtTopBar  = findViewById(R.id.topAppBar);
        tilTutorEmail    = findViewById(R.id.tilTutorLoginEmail);
        tilTutorPassword = findViewById(R.id.tilTutorLoginPassword);
        etTutorEmail     = tilTutorEmail.getEditText();
        etTutorPassword  = tilTutorPassword.getEditText();

        authenticationHandler = new AuthenticationHandler();

        mtTopBar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        btnLogin.setOnClickListener(v -> login());
        btnSignUp.setOnClickListener(v -> startActivity(new Intent(
                TutorLoginActivity.this,
                TutorSignUpActivity.class)));
    }

    private void login()
    {
        String email    = etTutorEmail.getText().toString().trim();
        String password = etTutorPassword.getText().toString().trim();
        try {
            tilTutorEmail.setError(null);
            tilTutorPassword.setError(null);
            InputValidator.validateEmail(email);
            InputValidator.validatePassword(password);
            ITutor tutor = authenticationHandler.authenticateTutor(email, password);
            TRData.setCurrentTutorID(tutor.getTutorID());

            Intent intent = new Intent(TutorLoginActivity.this,
                    TutorHomeActivity.class);

            startActivity(intent);
            finish();
        } catch (final InvalidEmailException e) {
            tilTutorEmail.setError(e.getMessage());
        } catch (final InvalidPasswordException e) {
            tilTutorPassword.setError(e.getMessage());
        } catch (final Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
