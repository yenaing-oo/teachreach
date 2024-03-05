package comp3350.teachreach.presentation.profile;

//package comp3350.teachreach.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.presentation.booking.BookingActivity;

public class TutorProfileActivity extends AppCompatActivity {

    private TextView tvCourses, tvPrice, tvRating, tvAvailability, tvPreferredCourse;
    private Button btnBookSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        tvCourses = findViewById(R.id.tvCourses);
        tvPrice = findViewById(R.id.tvPrice);
        tvRating = findViewById(R.id.tvRating);
        tvPreferredCourse = findViewById(R.id.tvPreferredCourse);
        btnBookSession = findViewById(R.id.btnBookSession);

//        // Populate these views with real data from your database or passed from the previous activity
//
//        ITutorProfile tutorProfile =
//                new TutorProfile(
//                        getIntent().getStringExtra("TUTOR_EMAIL_KEY"),
//                        Server.getTutorDataAccess());
//
//        tvCourses.setText(tutorProfile.getCourses().toString());
//        tvPrice.setText(String.valueOf(tutorProfile.getHourlyRate()));
//        tvRating.setText(String.valueOf(tutorProfile.getAvgReview()));

        btnBookSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorProfileActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateTutorProfile(ITutorProfile tutorProfile) {
        tvCourses.setText(formatCourses(tutorProfile.getCourses()));
        tvPrice.setText(String.format("$%.2f/hr", tutorProfile.getHourlyRate()));
        tvRating.setText(String.format("%.1f Stars", tutorProfile.getAvgReview()));
        // Further populate availability and preferred courses similarly
    }

    private String formatCourses(List<ICourse> courses) {
        StringBuilder coursesStr = new StringBuilder();
        for (ICourse course : courses) {
            coursesStr.append(course.toString()).append("\n");
        }
        return coursesStr.toString();
    }
}
