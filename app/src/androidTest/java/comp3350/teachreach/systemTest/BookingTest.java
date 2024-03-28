package comp3350.teachreach.systemTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.espresso.contrib.PickerActions.setDate;






import static org.hamcrest.Matchers.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.teachreach.R;
import comp3350.teachreach.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class BookingTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);


    // Helper method to perform login
    private void performLogin() {

        onView(withId(R.id.btnStudentLogin)).perform(click());
        onView(withId(R.id.etStudentLoginEmail)).perform(typeText("student1@email.com"), closeSoftKeyboard());
        onView(withId(R.id.etStudentLoginPassword)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.btnLoginAsStudent)).perform(click());
        onView(withId(R.id.NavBarSearch)).perform(click());
        onView(withId(R.id.searchField)).perform(typeText("COMP 3010"), closeSoftKeyboard());
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.rvSearchResult)).perform(click());

    }
    //actually testing payment here
    @Test
    public void testSuccessfulBooking() {
        performLogin();
        // Open the calendar view
       // onView(withId(R.id.cvCalendarBook)).perform(setDate(2024, 3, 30));



        onView(allOf(withId(R.id.timeSlotText), withText("9:30"))).perform(click());
        // Confirm the selection with the next steps of your booking process
        onView(withId(R.id.confirmButton)).perform(click());
        onView(withId(R.id.confirmButton)).perform(click());

        onView(withId(R.id.tilCardNumber)).perform(scrollTo(), click());
        onView(withId(R.id.tilCardNumber)).perform(replaceText("1234567890123456"), closeSoftKeyboard());

        onView(withId(R.id.tilExpDate)).perform(click());
        onView(withId(R.id.tilExpDate)).perform(replaceText("1224"));

        onView(withId(R.id.tilCVC)).perform(click());
        onView(withId(R.id.tilCVC)).perform(replaceText("123"), closeSoftKeyboard());

        onView(withId(R.id.confirmButton)).perform(click());

        onView(withText("Congratulations!")).check(matches(isDisplayed()));
        onView(withText("Booking request has been sent to your tutor!")).check(matches(isDisplayed()));





    }

}
