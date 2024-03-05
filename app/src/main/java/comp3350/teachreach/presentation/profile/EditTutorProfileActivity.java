package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.logic.interfaces.ITutorProfile;
import comp3350.teachreach.logic.profile.TutorProfile;

public class EditTutorProfileActivity extends AppCompatActivity {

    private EditText etPrice, etPreferredLocation;
    private Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor_profile);

        etPrice = findViewById(R.id.etPrice);
        etPreferredLocation = findViewById(R.id.etPreferredLocation);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        String tutorEmail = getIntent().getStringExtra("TUTOR_EMAIL_KEY");
        ITutorProfile tutorProfile = new TutorProfile(tutorEmail);

        initializeFields(tutorProfile);

        btnSaveChanges.setOnClickListener(view -> saveProfileChanges(tutorProfile));
    }

    private void initializeFields(ITutorProfile tutorProfile) {
        etPrice.setText(String.format("%.2f", tutorProfile.getHourlyRate()));
        String preferredLocations = String.join(", ", tutorProfile.getPreferredLocations());
        etPreferredLocation.setText(preferredLocations);
    }

    private void saveProfileChanges(ITutorProfile tutorProfile) {
        try {
            double price = Double.parseDouble(etPrice.getText().toString());
            tutorProfile.setHourlyRate(price);

            List<String> locations = Arrays.stream(etPreferredLocation.getText().toString().split("\\s*,\\s*"))
                    .collect(Collectors.toList());
            tutorProfile.addPreferredLocations(locations);

            tutorProfile.updateUserProfile();

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
        }
    }
}
