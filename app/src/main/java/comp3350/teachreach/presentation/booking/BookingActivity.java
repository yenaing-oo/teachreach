package comp3350.teachreach.presentation.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import comp3350.teachreach.R;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.presentation.utils.DateUtils;

public
class BookingActivity extends AppCompatActivity
        implements OnDateChangeListener, OnTimeSlotSelectedListener {
    private FragmentManager fragmentManager;
    private TextView dateDisplay;
    private int tutorID;
    private ITimeSlice selectedTimeSlot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        tutorID = getIntent().getIntExtra("TUTOR_ID", -1);

        dateDisplay = findViewById(R.id.dateDisplayTextView);
        MaterialButton reviewBookingButton = findViewById(R.id.review_booking_button);

        dateDisplay.setVisibility(View.GONE);
        reviewBookingButton.setEnabled(false);

        fragmentManager = getSupportFragmentManager();
        addDateSelectionFragment();
    }

    private void addDateSelectionFragment() {
        DateSelectionFragment fragment = DateSelectionFragment.newInstance();
        fragment.setOnDateChangeListener(this);
        fragmentManager
                .beginTransaction()
                .replace(R.id.datePickerFragmentView, fragment, "fragment")
                .commit();
    }

    private void addTimeSlotSelectionFragment(int year, int month, int dayOfMonth) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.timeSlotPickerFragmentView,
                        TimeSlotSelectionFragment.newInstance(this.tutorID, year, month, dayOfMonth),
                        "fragment")
                .commit();
    }

    @Override
    public void onDateSelected(int year, int month, int dayOfMonth) {
        dateDisplay.setText(DateUtils.formatDate(year, month, dayOfMonth));
        dateDisplay.setVisibility(View.VISIBLE);
        addTimeSlotSelectionFragment(year, month + 1, dayOfMonth);
    }

    @Override
    public void onTimeSlotSelected(ITimeSlice timeSlot) {
        this.selectedTimeSlot = timeSlot;
    }
}