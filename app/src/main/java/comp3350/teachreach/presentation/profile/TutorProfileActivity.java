package comp3350.teachreach.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.presentation.booking.BookingActivity;
import comp3350.teachreach.presentation.utils.TutorProfileFormatter;

public
class TutorProfileActivity extends AppCompatActivity
{

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        TextView tvCourses      = findViewById(R.id.tvCourses);
        TextView tvPrice        = findViewById(R.id.tvPrice);
        TextView tvRating       = findViewById(R.id.tvRating);
        Button   btnBookSession = findViewById(R.id.btnBookSession);

        ITutorProfileHandler tutorProfile
                = new TutorProfileHandler(getIntent().getIntExtra(
                "TUTOR_EMAIL_KEY",
                -1));
        TutorProfileFormatter tutorProfileFormatter = new TutorProfileFormatter(
                tutorProfile);

        tvCourses.setText(tutorProfileFormatter.getCourses());
        tvPrice.setText(String.valueOf(tutorProfileFormatter.getHourlyRate()));
        tvRating.setText(String.valueOf(tutorProfileFormatter.getRating()));

        btnBookSession.setOnClickListener(v -> {
            Intent intent = new Intent(TutorProfileActivity.this,
                                       BookingActivity.class);
            startActivity(intent);
        });
    }
}
