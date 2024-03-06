package comp3350.teachreach.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.logic.interfaces.IUserProfile;
import comp3350.teachreach.logic.profile.StudentProfileFetcher;

public
class EditUserProfileActivity extends AppCompatActivity
{

    private EditText etName, etPronoun, etMajor;
    private Button       btnSaveProfile;
    private IUserProfile userProfile;

    private IStudentPersistence studentPersistence;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        initializeViews();
        loadUserProfile();

        btnSaveProfile.setOnClickListener(v -> saveUserProfile());
    }

    private
    void initializeViews()
    {
        etName         = findViewById(R.id.etName);
        etPronoun      = findViewById(R.id.etPronoun);
        etMajor        = findViewById(R.id.etMajor);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
    }

    private
    void loadUserProfile()
    {
        // Retrieve the email from intent or another source
        String studentEmail = getIntent().getStringExtra("USER_EMAIL");

        try {
            userProfile = new StudentProfileFetcher(studentEmail);
            etName.setText(userProfile.getUserName());
            etPronoun.setText(userProfile.getUserPronouns());
            etMajor.setText(userProfile.getUserMajor());
        } catch (Exception e) { // Catching a broader exception for simplicity
            Toast
                    .makeText(this,
                              "User profile not found.",
                              Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    private
    void saveUserProfile()
    {
        if (userProfile != null) {
            String name    = etName.getText().toString();
            String pronoun = etPronoun.getText().toString();
            String major   = etMajor.getText().toString();

            userProfile.setUserName(name);
            userProfile.setUserPronouns(pronoun);
            userProfile.setUserMajor(major);
            userProfile.updateUserProfile();

            Intent data = new Intent();
            data.putExtra("UPDATED_NAME", name);
            data.putExtra("UPDATED_PRONOUN", pronoun);
            data.putExtra("UPDATED_MAJOR", major);
            setResult(RESULT_OK, data);

            Toast
                    .makeText(this,
                              "Profile updated successfully!",
                              Toast.LENGTH_SHORT)
                    .show();
            finish(); // Close activity and return
        } else {
            Toast
                    .makeText(this,
                              "Error updating profile. Please try again.",
                              Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
