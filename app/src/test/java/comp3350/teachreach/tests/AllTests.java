package comp3350.teachreach.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.teachreach.tests.logic.AccountCreatorTest;
import comp3350.teachreach.tests.logic.BookingHandlerTest;
import comp3350.teachreach.tests.logic.CredentialHandlerTest;
import comp3350.teachreach.tests.logic.SearchSortHandlerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountCreatorTest.class,
        BookingHandlerTest.class,
        CredentialHandlerTest.class,
        SearchSortHandlerTest.class
})


public class AllTests {

}
