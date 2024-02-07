//package comp3350.teachreach.application;
package comp3350.teachreach.presentation;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
/*import comp3350.teachreach.logic.AccountCreator;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.AccountType;*/

public class StudentSignupActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail, etMajor, etPronoun;
    private Button btnCreateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etMajor = findViewById(R.id.etMajor);
        etPronoun = findViewById(R.id.etPronoun);
        btnCreateProfile = findViewById(R.id.btnCreateProfile);

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

       /* AccountCreator accountCreator = new AccountCreator();
        try {
            Account newAccount = accountCreator.createAccount(AccountType.Student, username, pronoun, major, email, password);
            // Assuming successful account creation
            Toast.makeText(StudentSignupActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentSignupActivity.this, StudentProfileActivity.class);
            //  pass account details to the profile activity if needed
            startActivity(intent);
            finish(); // Close the signup activity
        } catch (Exception e) {
            // Handle any exceptions, like invalid email
            Toast.makeText(StudentSignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }*/
    }
}
