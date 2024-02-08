package comp3350.teachreach.tests;

import comp3350.teachreach.tests.logic.AccountCreatorTest;
import comp3350.teachreach.tests.logic.LoginHandlerTest;
import comp3350.teachreach.tests.logic.SearchSortHandlerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AccountCreatorTest.class, LoginHandlerTest.class, SearchSortHandlerTest.class})
public class AllTests {}
