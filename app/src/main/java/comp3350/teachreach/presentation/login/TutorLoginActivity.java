package comp3350.teachreach.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.account.AuthenticationHandler;
import comp3350.teachreach.logic.account.InputValidator;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.profile.TutorProfileActivity;
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

        tilTutorEmail    = findViewById(R.id.tilTutorLoginEmail);
        tilTutorPassword = findViewById(R.id.tilTutorLoginPassword);
        etTutorEmail     = tilTutorEmail.getEditText();
        etTutorPassword  = tilTutorPassword.getEditText();
        Button btnLogin  = findViewById(R.id.btnLoginAsTutor);
        Button btnSignUp = findViewById(R.id.btnSignupAsTutor);

        authenticationHandler = new AuthenticationHandler();

        btnLogin.setOnClickListener(v -> login());

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(TutorLoginActivity.this,
                                       TutorSignUpActivity.class);
            startActivity(intent);
        });
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
            ITutor tutor = authenticationHandler.authenticateTutor(email,
                                                                   password);

            Intent intent = new Intent(TutorLoginActivity.this,
                                       TutorProfileActivity.class);
            intent.putExtra("TUTOR_ID", tutor.getTutorID());
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
