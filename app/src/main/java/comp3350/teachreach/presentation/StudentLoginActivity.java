package comp3350.teachreach.presentation;

//package comp3350.teachreach.application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import comp3350.teachreach.R;


public class StudentLoginActivity extends AppCompatActivity {

    private EditText etStudentId, etStudentPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etStudentId = findViewById(R.id.etStudentId);
        etStudentPassword = findViewById(R.id.etStudentPassword);
        btnLogin = findViewById(R.id.btnStudentLogin);
        tvSignUp = findViewById(R.id.tvStudentSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUp Activity
                Intent intent = new Intent(StudentLoginActivity.this, StudentSignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String studentId = etStudentId.getText().toString().trim();
        String password = etStudentPassword.getText().toString().trim();

        if (validateInputs(studentId, password)) {
            // TODO: Implement actual login logic here
            // This is where you would authenticate the user

            // If login is successful, navigate to SearchActivity
            Intent intent = new Intent(StudentLoginActivity.this, SearchActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateInputs(String studentId, String password) {
        if (studentId.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Student ID and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Additional validation logic can be added here
        return true;
    }
}
