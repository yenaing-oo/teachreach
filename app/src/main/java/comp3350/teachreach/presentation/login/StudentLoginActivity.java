package comp3350.teachreach.presentation.login;

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
import comp3350.teachreach.presentation.home.SearchActivity;
import comp3350.teachreach.presentation.signup.StudentSignUpActivity;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText etStudentEmail, etStudentPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    private CredentialHandler credentialHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etStudentEmail = findViewById(R.id.etStudentEmail);
        etStudentPassword = findViewById(R.id.etStudentPassword);
        btnLogin = findViewById(R.id.btnStudentLogin);
        tvSignUp = findViewById(R.id.tvStudentSignUp);

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
                Intent intent = new Intent(StudentLoginActivity.this, StudentSignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String studentEmail = etStudentEmail.getText().toString().trim();
        String password = etStudentPassword.getText().toString().trim();

//        if (validateInputs(studentEmail, password)) {
        try {
            final boolean correctCredential =
                    credentialHandler.validateCredential(studentEmail, password);
            if (correctCredential) {
                // If the credentials are correct, navigate to the SearchActivity
                Intent intent = new Intent(StudentLoginActivity.this, SearchActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            } else {
                Toast.makeText(this, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Account does not exist",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
