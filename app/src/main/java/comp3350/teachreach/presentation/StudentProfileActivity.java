package comp3350.teachreach.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
    btnGoToSearch = findViewById(R.id.btnGoToSearch);

    Intent intent = getIntent();
    if (intent != null) {
      String name = intent.getStringExtra("STUDENT_NAME");
      String pronoun = intent.getStringExtra("STUDENT_PRONOUN");
      String major = intent.getStringExtra("STUDENT_MAJOR");

      tvName.setText(name != null ? name : "Name not provided");
      tvPronoun.setText(pronoun != null ? pronoun : "Pronoun not provided");
      tvMajor.setText(major != null ? major : "Major not provided");
    }

    btnGoToSearch.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent searchIntent = new Intent(StudentProfileActivity.this, SearchActivity.class);
            startActivity(searchIntent);
          }
        });
  }
}
