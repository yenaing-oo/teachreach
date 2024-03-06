package comp3350.teachreach.presentation.signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.interfaces.IAccountCreator;
import comp3350.teachreach.presentation.profile.TutorProfileActivity;

public
class TutorSignUpActivity extends AppCompatActivity
{

    private static final int REQUEST_CODE_PICK_FILE = 1;

    private EditText etTutorUsername, etTutorPassword, etTutorEmail,
            etTutorMajor, etPronouns;
    private Button btnUploadTranscript, btnTutorSubmit;
    private TextView        tvVerificationOutput;
    private Uri             transcriptUri;
    // Uri of the selected transcript file
    private IAccountCreator accountCreator;

    private
    void createTutorProfile()
    {
        String username = etTutorUsername.getText().toString().trim();
        String password = etTutorPassword.getText().toString().trim();
        String email    = etTutorEmail.getText().toString().trim();
        String major    = etTutorMajor.getText().toString().trim();
        String pronoun  = etPronouns.getText().toString();

        try {
            accountCreator = accountCreator.createAccount(email, password);

            accountCreator.setTutorProfile(username, major, pronoun);

            Toast
                    .makeText(TutorSignUpActivity.this,
                              "Tutor Account Created Successfully!",
                              Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(TutorSignUpActivity.this,
                                       TutorProfileActivity.class);
            intent.putExtra("TUTOR_EMAIL_KEY", email);

            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast
                    .makeText(TutorSignUpActivity.this,
                              e.getMessage(),
                              Toast.LENGTH_LONG)
                    .show();
        }

        //        if (newTutor != null) {
        //            Toast.makeText(TutorSignUpActivity.this, "Tutor Account
        //            Created Successfully!", Toast.LENGTH_SHORT).show();
        //            Intent intent = new Intent(TutorSignUpActivity.this,
        //            TutorProfileActivity.class);
        //            startActivity(intent);
        //            finish(); // To prevent returning to the signup screen
        //        } else {
        //            Toast.makeText(TutorSignUpActivity.this, "Signup failed
        //            . Please check your inputs and try again.", Toast
        //            .LENGTH_LONG).show();
        //        }
    }

    private
    void openFilePicker()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allow any file type.
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent,
                                                        "Select a File to " +
                                                        "Upload"),
                                   REQUEST_CODE_PICK_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast
                    .makeText(this,
                              "Please install a File Manager.",
                              Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected
    void onActivityResult(int requestCode,
                          int resultCode,
                          @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                transcriptUri = data.getData();
                // Handle the transcript file.
            }
        }
    }

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_sign_up);

        etTutorUsername      = findViewById(R.id.etTutorUsername);
        etTutorPassword      = findViewById(R.id.etTutorPassword);
        etTutorEmail         = findViewById(R.id.etTutorEmail);
        etTutorMajor         = findViewById(R.id.etTutorMajor);
        etPronouns           = findViewById(R.id.etPronouns);
        btnUploadTranscript  = findViewById(R.id.btnUploadTranscript);
        btnTutorSubmit       = findViewById(R.id.btnTutorSubmit);
        tvVerificationOutput = findViewById(R.id.tvVerificationOutput);

        accountCreator = new AccountCreator();

        btnUploadTranscript.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public
            void onClick(View v)
            {
                openFilePicker();
            }
        });

        btnTutorSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public
            void onClick(View v)
            {
                createTutorProfile();
            }
        });

    }
}
