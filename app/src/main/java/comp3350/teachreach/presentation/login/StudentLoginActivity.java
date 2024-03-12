package comp3350.teachreach.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.account.CredentialHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.search.SearchActivity;
import comp3350.teachreach.presentation.signup.StudentSignUpActivity;

public
class StudentLoginActivity extends AppCompatActivity
{

    private EditText etStudentEmail, etStudentPassword;

    private CredentialHandler credentialHandler;

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

        credentialHandler = new CredentialHandler();

        btnLogin.setOnClickListener(v -> login());

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(StudentLoginActivity.this,
                                       StudentSignUpActivity.class);
            startActivity(intent);
        });
    }

    private
    void login()
    {
        String studentEmail = etStudentEmail.getText().toString().trim();
        String password     = etStudentPassword.getText().toString().trim();

        try {
            Optional<IAccount> theAccount
                    = credentialHandler.validateCredential(studentEmail,
                                                           password);
            if (theAccount.isPresent()) {
                // If the credentials are correct, navigate to the
                // SearchActivity
                Intent intent = new Intent(StudentLoginActivity.this,
                                           SearchActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            } else {
                Toast
                        .makeText(this,
                                  "Invalid email or password. Please try " +
                                  "again.",
                                  Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (Exception e) {
            Toast
                    .makeText(this,
                              "Account does not exist",
                              Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
