package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class CredentialHandlerTest {
    private ICredentialHandler credentialHandler;

    @Before
    public void setUp() throws AccountCreatorException {
        System.out.println("Starting a new test for CredentialHandler");
        IAccountPersistence accountsDataAccess = new AccountStub();
        IStudentPersistence studentsDataAccess = new StudentStub(accountsDataAccess);
        ITutorPersistence tutorsDataAccess = new TutorStub(accountsDataAccess);
        credentialHandler = new CredentialHandler(accountsDataAccess);

        AccountCreator accountCreator = new AccountCreator(
                accountsDataAccess,
                studentsDataAccess,
                tutorsDataAccess,
                credentialHandler);

        accountCreator.createAccount(
                        "fulopv@myumanitoba.ca",
                        "Nagyon jo")
                .setStudentProfile(
                        "Victoria",
                        "Education",
                        "She/Her")
                .setTutorProfile(
                        "Ms. Philip",
                        "Education",
                        "She/Her")
                .buildAccount();
    }

    @Test
    public void testValidateGoodCredential() {
        assertTrue(credentialHandler.validateCredential(
                "fulopv@myumanitoba.ca",
                "Nagyon jo"));
    }

    @Test
    public void testValidateBadCredentialBad() {
        assertFalse(credentialHandler.validateCredential(
                "fulopv@myumanitoba.ca",
                "Nagyon Jo"));
        assertFalse(credentialHandler.validateCredential(
                "philipv@myumanitoba.ca",
                "Nagyon jo"));
    }

    @Test
    public void testValidateEmptyCredential() {
        assertFalse(credentialHandler.validateCredential(
                "fulopv@myumanitoba.ca",
                ""));
        assertFalse(credentialHandler.validateCredential(
                "",
                "Nagyon jo"));
    }
}
