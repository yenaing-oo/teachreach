package comp3350.teachreach.presentation;

//package comp3350.teachreach.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.teachreach.R;

public class StudentProfileActivity extends AppCompatActivity {

    private TextView tvName, tvPronoun, tvMajor;
    private Button btnGoToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        tvName = findViewById(R.id.tvName);
        tvPronoun = findViewById(R.id.tvPronoun);
        tvMajor = findViewById(R.id.tvMajor);
        //etSearch = findViewById(R.id.etSearch);
        btnGoToSearch = findViewById(R.id.btnGoToSearch); // Initialize the button


        // Fetch and display student's profile data
        // For example:
        // tvName.setText("John Doe");
        // tvPronoun.setText("He/Him");
        // tvMajor.setText("Computer Science");

        // Handle search logic
        btnGoToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the SEARCH page
             //   Intent intent = new Intent(StudentProfileActivity.this, MainPageActivity.class);
              //  startActivity(intent);
            }
        });
    }
}
