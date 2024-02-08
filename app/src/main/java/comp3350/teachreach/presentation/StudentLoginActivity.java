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
import comp3350.teachreach.logic.LoginHandler;
import comp3350.teachreach.objects.AccountType;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText etStudentEmail, etStudentPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    private LoginHandler loginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etStudentEmail = findViewById(R.id.etStudentEmail);
        etStudentPassword = findViewById(R.id.etStudentPassword);
        btnLogin = findViewById(R.id.btnStudentLogin);
        tvSignUp = findViewById(R.id.tvStudentSignUp);

        loginHandler = new LoginHandler();

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

        if (validateInputs(studentEmail, password)) {
            if (loginHandler.validateCredential(AccountType.Student, studentEmail, password)) {
                // If the credentials are correct, navigate to the SearchActivity
                Intent intent = new Intent(StudentLoginActivity.this, SearchActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            } else {
                Toast.makeText(this, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs(String studentEmail, String password) {
        if (studentEmail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
