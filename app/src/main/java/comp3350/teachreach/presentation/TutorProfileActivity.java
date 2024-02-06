package comp3350.teachreach.presentation;

//package comp3350.teachreach.application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp3350.teachreach.R;

public class TutorProfileActivity extends AppCompatActivity {

    private TextView tvCourses, tvPrice, tvRating, tvAvailability, tvPreferredCourse;
    private Button btnUpcomingSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        tvCourses = findViewById(R.id.tvCourses);
        tvPrice = findViewById(R.id.tvPrice);
        tvRating = findViewById(R.id.tvRating);
        tvAvailability = findViewById(R.id.tvAvailability);
        tvPreferredCourse = findViewById(R.id.tvPreferredCourse);
        btnUpcomingSessions = findViewById(R.id.btnUpcomingSessions);

        // Populate these views with real data from your database or passed from the previous activity

        btnUpcomingSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Upcoming Sessions UI
               // Intent intent = new Intent(TutorProfileActivity.this, UpcomingSessionsActivity.class);
                //startActivity(intent);
            }
        });
    }
}
