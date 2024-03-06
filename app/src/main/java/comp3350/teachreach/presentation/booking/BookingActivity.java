package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import comp3350.teachreach.R;
import comp3350.teachreach.utils.DateUtils;

public class BookingActivity extends AppCompatActivity implements OnDateChangeListener {
    private FragmentManager fragmentManager;
    private TextView dateDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dateDisplay = findViewById(R.id.dateDisplayTextView);
        dateDisplay.setVisibility(View.GONE);

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
        dateDisplay.setText(DateUtils.formatDate(year, month, dayOfMonth));
        dateDisplay.setVisibility(View.VISIBLE);
        addTimeSlotSelectionFragment();
    }

}