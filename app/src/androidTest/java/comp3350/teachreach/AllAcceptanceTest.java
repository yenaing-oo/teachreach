package comp3350.teachreach;




import androidx.test.filters.LargeTest;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import comp3350.teachreach.systemTest.TeachReachTest;
import comp3350.teachreach.systemTest.EditStudentProfileTest;
import comp3350.teachreach.systemTest.SearchTutorByCourseTest;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TeachReachTest.class,
        EditStudentProfileTest.class,
        SearchTutorByCourseTest.class

})

public class AllAcceptanceTest {
}
