package comp3350.teachreach;




import androidx.test.filters.LargeTest;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.teachreach.systemTest.EditStudentProfileTest;
import comp3350.teachreach.systemTest.TeachReachTest;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TeachReachTest.class,
        EditStudentProfileTest.class

})

public class AllAcceptanceTest {
}
