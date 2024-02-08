package comp3350.teachreach.presentation;

// package comp3350.teachreach.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import comp3350.teachreach.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btnStudent = findViewById(R.id.btnStudentLogin);
    Button btnTutor = findViewById(R.id.btnTutor);

    btnStudent.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);

            startActivity(intent);
          }
        });

    btnTutor.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TutorLoginActivity.class);
            startActivity(intent);
          }
        });
  }
}
