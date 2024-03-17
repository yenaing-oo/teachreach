package comp3350.teachreach.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.presentation.profile.EditTutorProfileActivity;

public class TutorHomeActivity extends AppCompatActivity
{
    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home);

        int tutorId = getIntent().getIntExtra("TUTOR_ID", -1);

        editProfileButton = findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditTutorProfileActivity.class);
            intent.putExtra("TUTOR_ID", tutorId);
            startActivity(intent);
        });
    }
}