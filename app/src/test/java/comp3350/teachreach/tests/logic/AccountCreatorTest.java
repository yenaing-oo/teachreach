//package comp3350.teachreach.tests.logic;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import comp3350.teachreach.data.stubs.AccountStub;
//import comp3350.teachreach.data.interfaces.IAccountPersistence;
//import comp3350.teachreach.data.interfaces.IStudentPersistence;
//import comp3350.teachreach.data.interfaces.ITutorPersistence;
//import comp3350.teachreach.data.stubs.StudentStub;
//import comp3350.teachreach.data.stubs.TutorStub;
//import comp3350.teachreach.logic.account.AccountCreator;
//import comp3350.teachreach.logic.account.AccountCreatorException;
//import comp3350.teachreach.logic.account.CredentialHandler;
//import comp3350.teachreach.logic.interfaces.IAccountCreator;
//import comp3350.teachreach.logic.interfaces.ICredentialHandler;
//import comp3350.teachreach.objects.interfaces.IAccount;
//
//public class AccountCreatorTest {
//    private IAccountCreator accountCreator;
//
//    @Before
//    public void setUp() {
//        System.out.println("Starting a new test for AccountCreator");
//        IAccountPersistence accountsDataAccess = new AccountStub();
//        IStudentPersistence studentsDataAccess = new StudentStub(accountsDataAccess);
//        ITutorPersistence tutorsDataAccess = new TutorStub(accountsDataAccess);
//        ICredentialHandler credentialHandler = new CredentialHandler(accountsDataAccess);
//
//
//        accountCreator = new AccountCreator(
//                accountsDataAccess,
//                studentsDataAccess,
//                tutorsDataAccess,
//                credentialHandler);
//    }
//
//    @Test
//    public void testCreateAccountBad() {
//        assertThrows(AccountCreatorException.class, () ->
//                accountCreator.createAccount(
//                        "rms.gnu.org",
//                        "defenestrate"));
//        assertThrows(AccountCreatorException.class, () ->
//                accountCreator.createAccount(
//                        "rms@gnu.org",
//                        ""));
//        assertThrows(AccountCreatorException.class, () ->
//                accountCreator.createAccount(
//                        "",
//                        "defenestrate"));
//    }
//
//    @Test
//    public void testCreateAccountGood() throws AccountCreatorException {
//        IAccount testAccount = accountCreator.createAccount(
//                        "r@google.com",
//                        "herpolhode")
//                .setStudentProfile(
//                        "Rob", "CS", "he")
//                .setTutorProfile(
//                        "Rob", "CS", "he")
//                .buildAccount();
//
//        assertNotNull(testAccount);
//        assertNotNull(testAccount.getStudentProfile());
//        assertNotNull(testAccount.getTutorProfile());
//        assertEquals("r@google.com", testAccount.getEmail());
//    }
//}
