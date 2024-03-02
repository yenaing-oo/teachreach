package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import comp3350.teachreach.data.AccountStub;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.data.StudentStub;
import comp3350.teachreach.data.TutorStub;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.account.AccountCreatorException;
import comp3350.teachreach.logic.account.CredentialHandler;
import comp3350.teachreach.logic.account.ICredentialHandler;
import comp3350.teachreach.objects.IAccount;

public class AccountCreatorTest {
    private AccountCreator accountCreator;
    private IAccountPersistence accountsDataAccess;

    private ITutorPersistence tutorsDataAccess;
    private IStudentPersistence studentsDataAccess;
    private ICredentialHandler credentialHandler;

    @Before
    public void setUp() {
        System.out.println("Starting a new test for AccountCreator");
        accountsDataAccess = new AccountStub();
        studentsDataAccess = new StudentStub(accountsDataAccess);
        tutorsDataAccess = new TutorStub(accountsDataAccess);
        credentialHandler = new CredentialHandler(accountsDataAccess);


        accountCreator = new AccountCreator(
                accountsDataAccess,
                studentsDataAccess,
                tutorsDataAccess,
                credentialHandler);
    }

    @Test
    public void testCreateAccountBad() {
        assertThrows(AccountCreatorException.class, () ->
                accountCreator.createAccount(
                        "rms.gnu.org",
                        "defenestrate"));
        assertThrows(AccountCreatorException.class, () ->
                accountCreator.createAccount(
                        "rms@gnu.org",
                        ""));
        assertThrows(AccountCreatorException.class, () ->
                accountCreator.createAccount(
                        "",
                        "defenestrate"));
    }

    @Test
    public void testCreateAccountGood() throws AccountCreatorException {
        IAccount testAccount = accountCreator.createAccount(
                        "r@google.com",
                        "herpolhode")
                .setStudentProfile(
                        "Rob", "CS", "he")
                .setTutorProfile(
                        "Rob", "CS", "he")
                .buildAccount();

        assertNotNull(testAccount);
        assertNotNull(testAccount.getStudentProfile());
        assertNotNull(testAccount.getTutorProfile());
        assertEquals("r@google.com", testAccount.getEmail());
    }
}
