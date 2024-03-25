package comp3350.teachreach;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.teachreach.presentation.login.StudentLoginActivity;
import comp3350.teachreach.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TeachReachTest {

    @Rule
    public ActivityScenarioRule<StudentLoginActivity> activityRule =
            new ActivityScenarioRule<>(StudentLoginActivity.class);

    @Test
    public void testSuccessfulLogin() {
        onView(withId(R.id.tilStudentLoginEmail)).perform(typeText("student@email.com"), closeSoftKeyboard());
        onView(withId(R.id.tilStudentLoginPassword)).perform(typeText("123"), closeSoftKeyboard());

        onView(withId(R.id.btnLoginAsStudent)).perform(click());

        onView(withId(R.id.navigation_menu)).check(matches(isDisplayed()));
    }
}
