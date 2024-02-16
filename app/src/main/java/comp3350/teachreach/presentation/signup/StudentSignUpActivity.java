package comp3350.teachreach.presentation.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.AccountCreator;
import comp3350.teachreach.logic.IAccountCreator;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.presentation.home.SearchActivity;

public class StudentSignUpActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail, etMajor, etPronoun;
    private Button btnCreateProfile;
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
        btnCreateProfile = findViewById(R.id.btnCreateProfile);

        accountCreator = new AccountCreator();

        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });
    }

    private void createProfile() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String major = etMajor.getText().toString().trim();
        String pronoun = etPronoun.getText().toString().trim();

        Student newStudent = accountCreator.createStudentAccount(username, pronoun, major, email, password);

        if (newStudent != null) {
            Toast.makeText(StudentSignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentSignUpActivity.this, SearchActivity.class);
            intent.putExtra("STUDENT_NAME", newStudent.getName());
            intent.putExtra("STUDENT_PRONOUN", pronoun);
            intent.putExtra("STUDENT_MAJOR", major);

            startActivity(intent);
            finish();
        } else {
            Toast.makeText(StudentSignUpActivity.this, "Error: Invalid input", Toast.LENGTH_LONG).show();
        }
    }
}
