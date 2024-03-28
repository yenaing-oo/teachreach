package comp3350.teachreach.systemTest;



import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import android.view.View;
import com.google.android.material.tabs.TabLayout;
import org.hamcrest.Matcher;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import org.hamcrest.Matchers;

public class TabLayoutViewActions {
    public static ViewAction selectTabAtPosition(final int position) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return Matchers.allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return "Selecting Tab at position " + position;
            }

            @Override
            public void perform(UiController uiController, View view) {
                TabLayout tabLayout = (TabLayout) view;
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                if (tab != null) {
                    tab.select();
                }
            }
        };
    }
}

