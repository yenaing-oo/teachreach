//package comp3350.teachreach.tests.logic;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import comp3350.teachreach.data.stubs.AccountStub;
//import comp3350.teachreach.data.interfaces.IAccountPersistence;
//import comp3350.teachreach.data.stubs.SessionStub;
//import comp3350.teachreach.logic.BookingHandler;
//import comp3350.teachreach.objects.Student;
//
//public class BookingHandlerTest {
//    private SessionStub sessionStub;
//    private IAccountPersistence accountPersistence;
//    private BookingHandler bookingHandler;
//
//    @Before
//    public void setUp() {
//        sessionStub = new SessionStub();
//        accountPersistence = new AccountStub();
//        bookingHandler = new BookingHandler(accountPersistence, sessionStub);
//    }
//
//    @Test
//    public void testGetListOfSession() {
//        assertEquals(1, bookingHandler.getListOfSession().size());
//        sessionStub.removeSession(bookingHandler.getListOfSession().get(0));
//        assertEquals(0, bookingHandler.getListOfSession().size());
//    }
//
//    @Test
//    public void testGetStudentByEmail() {
//        Student newTestStudent = accountPersistence.storeStudent(new Student(
//                "Name",
//                "Pronouns",
//                "Major", "email@example.com", "PWD"));
//
//        assertEquals(1, bookingHandler.getListOfSession().size());
//        sessionStub.removeSession(bookingHandler.getListOfSession().get(0));
//        assertEquals(0, bookingHandler.getListOfSession().size());
//    }
//}
