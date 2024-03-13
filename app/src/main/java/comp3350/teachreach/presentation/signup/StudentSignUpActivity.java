package comp3350.teachreach.presentation.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.interfaces.IAccountCreator;
import comp3350.teachreach.presentation.search.SearchActivity;

public
class StudentSignUpActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail, etMajor, etPronoun;
    private IAccountCreator accountCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etMajor = findViewById(R.id.etMajor);
        etPronoun = findViewById(R.id.etPronoun);
        Button btnCreateProfile = findViewById(R.id.btnCreateProfile);

        accountCreator = new AccountCreator();

        btnCreateProfile.setOnClickListener(v -> createProfile());
    }

    private void createProfile() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String major = etMajor.getText().toString().trim();
        String pronoun = etPronoun.getText().toString().trim();

        try {
            accountCreator.createStudentAccount(email, password, username, pronoun, major);
            Toast
                    .makeText(
                            StudentSignUpActivity.this,
                            "Account Created Successfully!",
                            Toast.LENGTH_SHORT
                    )
                    .show();
            Intent intent = new Intent(StudentSignUpActivity.this, SearchActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast
                    .makeText(
                            StudentSignUpActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG
                    )
                    .show();
        }
    }
}
