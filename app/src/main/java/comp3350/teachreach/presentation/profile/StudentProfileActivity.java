package comp3350.teachreach.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.profile.IUserProfile;
import comp3350.teachreach.presentation.home.SearchActivity;

public class StudentProfileActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST = 1; // Request code for editing profile

    private TextView tvName, tvPronoun, tvMajor;
    private Button btnGoToSearch, btnEditProfile;
    private IUserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        initializeViews();
        extractDataFromIntent();
        setupGoToSearchButton();
        setupEditProfileButton();
    }

    private void initializeViews() {
        tvName = findViewById(R.id.tvName);
        tvPronoun = findViewById(R.id.tvPronoun);
        tvMajor = findViewById(R.id.tvMajor);
        btnGoToSearch = findViewById(R.id.btnGoToSearch);
        btnEditProfile = findViewById(R.id.btnEditProfile);
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("STUDENT_NAME");
            String pronoun = intent.getStringExtra("STUDENT_PRONOUN");
            String major = intent.getStringExtra("STUDENT_MAJOR");
            updateProfileViews(name, pronoun, major);
        }
    }

    private void updateProfileViews(String name, String pronoun, String major) {
        tvName.setText(name != null ? name : "Name not provided");
        tvPronoun.setText(pronoun != null ? pronoun : "Pronoun not provided");
        tvMajor.setText(major != null ? major : "Major not provided");
    }

    private void setupGoToSearchButton() {
        btnGoToSearch.setOnClickListener(v -> navigateToSearch());
    }

    private void navigateToSearch() {
        Intent searchIntent = new Intent(StudentProfileActivity.this, SearchActivity.class);
        startActivity(searchIntent);
    }

    private void setupEditProfileButton() {
        btnEditProfile.setOnClickListener(v -> startEditProfileActivity());
    }

    private void startEditProfileActivity() {
        Intent editIntent = new Intent(this, EditUserProfileActivity.class);
        startActivityForResult(editIntent, EDIT_PROFILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("UPDATED_NAME");
            String updatedPronoun = data.getStringExtra("UPDATED_PRONOUN");
            String updatedMajor = data.getStringExtra("UPDATED_MAJOR");
            updateProfileViews(updatedName, updatedPronoun, updatedMajor);
        }
    }
}
