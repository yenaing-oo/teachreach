package comp3350.teachreach.presentation.booking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import comp3350.teachreach.R;

public class BookingActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        fragmentManager = getSupportFragmentManager();
        addDateSelectionFragment();

    }

    private void addDateSelectionFragment() {
        fragmentManager
                .beginTransaction()
                .add(R.id.fragmentView, DateSelectionFragment.newInstance("email"), "fragment")
                .commit();
    }
}