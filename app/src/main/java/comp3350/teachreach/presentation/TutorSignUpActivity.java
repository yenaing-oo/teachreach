package comp3350.teachreach.presentation;

//package comp3350.teachreach.application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import comp3350.teachreach.R;

public class TutorSignUpActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_FILE = 1;

    private EditText etTutorUsername, etTutorPassword, etTutorEmail, etTutorMajor;
    private Spinner spinnerCourses;
    private Button btnUploadTranscript, btnTutorSubmit;
    private TextView tvVerificationOutput;
    private Uri transcriptUri; // Uri of the selected transcript file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_sign_up);

        etTutorUsername = findViewById(R.id.etTutorUsername);
        etTutorPassword = findViewById(R.id.etTutorPassword);
        etTutorEmail = findViewById(R.id.etTutorEmail);
        etTutorMajor = findViewById(R.id.etTutorMajor);
        spinnerCourses = findViewById(R.id.spinnerCourses);
        btnUploadTranscript = findViewById(R.id.btnUploadTranscript);
        btnTutorSubmit = findViewById(R.id.btnTutorSubmit);
        tvVerificationOutput = findViewById(R.id.tvVerificationOutput);

        btnUploadTranscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        btnTutorSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input and handle form submission
                // Implement your logic for handling the signup process here
                boolean isSignupSuccessful = true; // This should be set based on the actual signup success

                if (isSignupSuccessful) {
                    // Navigate to Tutor Profile Activity
                    Intent intent = new Intent(TutorSignUpActivity.this, TutorProfileActivity.class);
                    startActivity(intent);
                    finish(); //to prevent returning to the signup screen
                } else {
                    // Handle the case where signup is not successful
                }
            }
        });

        // Add logic to populate and handle spinnerCourses
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allow any file type.
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_CODE_PICK_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                transcriptUri = data.getData();
                // Handle the transcript file. You might want to upload it to your server or store its path
            }
        }
    }
}
