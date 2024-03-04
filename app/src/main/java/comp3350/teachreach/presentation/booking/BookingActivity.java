package comp3350.teachreach.presentation.booking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import comp3350.teachreach.R;

public class BookingActivity extends AppCompatActivity implements OnDateChangeListener {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        fragmentManager = getSupportFragmentManager();
        addDateSelectionFragment();

    }

    private void addDateSelectionFragment() {
        DateSelectionFragment fragment = DateSelectionFragment.newInstance("email");
        fragment.setOnDateChangeListener(this);
        fragmentManager
                .beginTransaction()
                .replace(R.id.datePickerFragmentView, fragment, "fragment")
                .commit();
    }

    private void addTimeSlotSelectionFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.timeSlotPickerFragmentView, TimeSlotSelectionFragment.newInstance(), "fragment")
                .commit();
    }

    @Override
    public void onDateChanged(int year, int month, int dayOfMonth) {
        addTimeSlotSelectionFragment();
    }
}