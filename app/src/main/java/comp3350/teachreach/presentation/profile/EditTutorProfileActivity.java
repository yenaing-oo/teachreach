package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import comp3350.teachreach.R;
import comp3350.teachreach.application.TRData;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;

public
class EditTutorProfileActivity extends AppCompatActivity
{

    private EditText etPrice, etPreferredLocation;
    private Button btnSaveChanges;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor_profile);

        etPrice             = findViewById(R.id.etPrice);
        etPreferredLocation = findViewById(R.id.etPreferredLocation);
        btnSaveChanges      = findViewById(R.id.btnSaveChanges);

        ITutorProfileHandler tutorProfile = new TutorProfileHandler(TRData.getCurrentTutorID());

        initializeFields(tutorProfile);

        btnSaveChanges.setOnClickListener(view -> saveProfileChanges(
                tutorProfile));
    }

    private
    void initializeFields(ITutorProfileHandler tutorProfile)
    {
        etPrice.setText(String.format(Locale.US,
                                      "%.2f",
                                      tutorProfile.getHourlyRate()));
        String preferredLocations = String.join(", ",
                                                tutorProfile.getPreferredLocations());
        etPreferredLocation.setText(preferredLocations);
    }

    private
    void saveProfileChanges(ITutorProfileHandler tutorProfile)
    {
        try {
            double price = Double.parseDouble(etPrice.getText().toString());
            tutorProfile.setHourlyRate(price);

            List<String> locations = Arrays
                    .stream(etPreferredLocation
                                    .getText()
                                    .toString()
                                    .split("\\s*,\\s*"))
                    .collect(Collectors.toList());
            tutorProfile.addPreferredLocations(locations);

            tutorProfile.updateTutorProfile();

            Toast
                    .makeText(this,
                              "Profile updated successfully",
                              Toast.LENGTH_SHORT)
                    .show();
            finish();
        } catch (NumberFormatException e) {
            Toast
                    .makeText(this, "Invalid price format", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
