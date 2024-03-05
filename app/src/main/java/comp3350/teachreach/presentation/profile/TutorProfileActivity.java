package comp3350.teachreach.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.Server;
import comp3350.teachreach.logic.profile.ITutorProfile;
import comp3350.teachreach.logic.profile.TutorProfile;
import comp3350.teachreach.objects.Course;

public class TutorProfileActivity extends AppCompatActivity {

    private TextView tvCourses, tvPrice, tvRating, tvAvailability, tvPreferredCourse;
    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        tvCourses = findViewById(R.id.tvCourses);
        tvPrice = findViewById(R.id.tvPrice);
        tvRating = findViewById(R.id.tvRating);
        tvAvailability = findViewById(R.id.tvAvailability);
        tvPreferredCourse = findViewById(R.id.tvPreferredCourse);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        String tutorEmail = getIntent().getStringExtra("TUTOR_EMAIL_KEY");
        ITutorProfile tutorProfile = new TutorProfile(tutorEmail, Server.getTutorDataAccess());

        // Populate the UI with the tutor's profile data
        populateTutorProfile(tutorProfile);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(TutorProfileActivity.this, EditTutorProfileActivity.class);
            intent.putExtra("TUTOR_EMAIL_KEY", tutorEmail);
            startActivity(intent);
        });
    }

    private void populateTutorProfile(ITutorProfile tutorProfile) {
        tvCourses.setText(formatCourses(tutorProfile.getCourses()));
        tvPrice.setText(String.format("$%.2f/hr", tutorProfile.getHourlyRate()));
        tvRating.setText(String.format("%.1f Stars", tutorProfile.getAvgReview()));
        // Further populate availability and preferred courses similarly
    }

    private String formatCourses(List<Course> courses) {
        StringBuilder coursesStr = new StringBuilder();
        for (Course course : courses) {
            coursesStr.append(course.toString()).append("\n");
        }
        return coursesStr.toString();
    }
}
