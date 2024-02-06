package comp3350.teachreach.presentation;

//package comp3350.teachreach.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.teachreach.R;

public class TutorLoginActivity extends AppCompatActivity {

    private EditText etTutorEmail, etTutorPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_login);

        etTutorEmail = findViewById(R.id.etTutorEmail);
        etTutorPassword = findViewById(R.id.etTutorPassword);
        btnLogin = findViewById(R.id.btnTutorLogin);
        tvSignUp = findViewById(R.id.tvTutorSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login logic for tutor
               // login();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Tutor Sign Up Activity
                Intent intent = new Intent(TutorLoginActivity.this, TutorSignupActivity.class);
                startActivity(intent);
            }
        });
    }
}