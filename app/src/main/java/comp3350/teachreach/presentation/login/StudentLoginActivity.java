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
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.presentation.search.SearchActivity;
import comp3350.teachreach.presentation.signup.StudentSignUpActivity;

public
class StudentLoginActivity extends AppCompatActivity
{

    private EditText etStudentEmail, etStudentPassword;

    private AuthenticationHandler authenticationHandler;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etStudentEmail    = findViewById(R.id.etStudentEmail);
        etStudentPassword = findViewById(R.id.etStudentPassword);
        Button   btnLogin = findViewById(R.id.btnStudentLogin);
        TextView tvSignUp = findViewById(R.id.tvStudentSignUp);

        authenticationHandler = new AuthenticationHandler();

        btnLogin.setOnClickListener(v -> login());

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(StudentLoginActivity.this,
                                       StudentSignUpActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String email = etStudentEmail.getText().toString().trim();
        String password = etStudentPassword.getText().toString().trim();

        try {
            InputValidator.validateEmail(email);
            InputValidator.validatePassword(password);
            IStudent student = authenticationHandler.authenticateStudent(email, password);
            TRData.setCurrentStudentID(student.getStudentID());
            Intent intent = new Intent(StudentLoginActivity.this, SearchActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
