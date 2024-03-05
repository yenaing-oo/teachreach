package comp3350.teachreach.presentation.signup;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.account.IAccountCreator;

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

//        accountCreator = new AccountCreator(Server.getAccountDataAccess(),
//                Server.getStudentDataAccess(),
//                Server.getTutorDataAccess(),
//                new CredentialHandler(Server.getAccountDataAccess()));
//
//        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createProfile();
//            }
//        });
    }

    private void createProfile() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String major = etMajor.getText().toString().trim();
        String pronoun = etPronoun.getText().toString().trim();

//        try {
//            IStudent theStudent = accountCreator
//                    .createAccount(
//                            email,
//                            password)
//                    .setStudentProfile(
//                            username,
//                            major,
//                            pronoun)
//                    .buildAccount()
//                    .getStudentProfile()
//                    .orElseThrow(
//                            () -> new AccountCreatorException(
//                                    "Failed while creating a new Account"));
//
//            Intent intent = new Intent(
//                    StudentSignUpActivity.this, SearchActivity.class);
//            intent.putExtra("STUDENT_NAME", theStudent.getName());
//            intent.putExtra("STUDENT_PRONOUN", theStudent.getPronouns());
//            intent.putExtra("STUDENT_MAJOR", theStudent.getMajor());
//
//            startActivity(intent);
//            finish();
//        } catch (Exception e) {
//            Toast.makeText(StudentSignUpActivity.this, e.getMessage(),
//                    Toast.LENGTH_LONG).show();
//        }

//        if (newStudent != null) {
//            Toast.makeText(StudentSignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(StudentSignUpActivity.this, SearchActivity.class);
//            Student theStudent = newStudent.getStudentProfile().get();
//            intent.putExtra("STUDENT_NAME", theStudent.getName());
//            intent.putExtra("STUDENT_PRONOUN", pronoun);
//            intent.putExtra("STUDENT_MAJOR", major);
//
//            startActivity(intent);
//            finish();
//        } else {
//            Toast.makeText(StudentSignUpActivity.this, "Error: Invalid input", Toast.LENGTH_LONG).show();
//        }
    }
}
