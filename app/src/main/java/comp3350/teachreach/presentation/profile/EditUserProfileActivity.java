package comp3350.teachreach.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.account.AccountManager;
import comp3350.teachreach.logic.interfaces.IAccountManager;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.logic.profile.StudentProfileHandler;

public
class EditUserProfileActivity extends AppCompatActivity
{

    private EditText etName, etPronoun, etMajor;
    private Button              btnSaveProfile;
    private IAccountManager     accountManager;
    private IUserProfileHandler userProfileHandler;
    private AccessAccounts      accessAccounts;
    private AccessStudents      accessStudents;

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
        int studentID = getIntent().getIntExtra("STUDENT_ID", -1);

        try {
            userProfileHandler
                    =
                    new StudentProfileHandler(accessStudents.getStudentByStudentID(
                    studentID));

            etName.setText(userProfileHandler.getUserName());
            etPronoun.setText(userProfileHandler.getUserPronouns());
            etMajor.setText(userProfileHandler.getUserMajor());
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
        int studentID = getIntent().getIntExtra("STUDENT_ID", -1);
        try {
            accountManager = new AccountManager(accessAccounts
                                                        .getAccounts()
                                                        .get(accessStudents
                                                                     .getStudentByStudentID(
                                                                             studentID)
                                                                     .getStudentAccountID()));
            String name    = etName.getText().toString();
            String pronoun = etPronoun.getText().toString();
            String major   = etMajor.getText().toString();

            accountManager
                    .updateAccountUsername(name)
                    .updateAccountUserPronouns(pronoun)
                    .updateAccountUserMajor(major);

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
        } catch (final Exception e) {
            Toast
                    .makeText(this,
                              "Error updating profile. Please try again.",
                              Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
