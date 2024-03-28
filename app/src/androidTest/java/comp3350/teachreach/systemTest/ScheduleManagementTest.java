// File: ScheduleManagementTest.java

package comp3350.teachreach.systemTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
public class ScheduleManagementTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    // Helper method to perform login
    private void performLogin() {
        onView(withId(R.id.btnStudentLogin)).perform(click());
        onView(withId(R.id.etStudentLoginEmail)).perform(typeText("student1@email.com"), closeSoftKeyboard());
        onView(withId(R.id.etStudentLoginPassword)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.btnLoginAsStudent)).perform(click());
    }

    @Test
    public void testSuccessfulSession() {
        performLogin();
        // Navigate to the session page
        onView(withId(R.id.NavBarSessions)).perform(click());

        // Click on the "Pending" tab at position 0 and verify session list is displayed
        onView(withId(R.id.sessionTabs)).perform(TabLayoutViewActions.selectTabAtPosition(0));
        onView(withId(R.id.rvSessionResult)).check(matches(isDisplayed()));

        // Click on the "Upcoming" tab at position 1 and verify session list is displayed
        onView(withId(R.id.sessionTabs)).perform(TabLayoutViewActions.selectTabAtPosition(1));
        onView(withId(R.id.rvSessionResult)).check(matches(isDisplayed()));

        // Click on the "Previous" tab at position 2 and verify session list is displayed
        onView(withId(R.id.sessionTabs)).perform(TabLayoutViewActions.selectTabAtPosition(2));
        onView(withId(R.id.rvSessionResult)).check(matches(isDisplayed()));
    }
}
