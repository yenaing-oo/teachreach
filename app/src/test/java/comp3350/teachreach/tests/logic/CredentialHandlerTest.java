package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.account.AccountCreatorException;
import comp3350.teachreach.logic.account.CredentialHandler;
import comp3350.teachreach.logic.interfaces.ICredentialHandler;

public
class CredentialHandlerTest
{
    private ICredentialHandler credentialHandler;

    @Before
    public
    void setUp() throws AccountCreatorException
    {
        System.out.println("Starting a new test for CredentialHandler");
        //        IAccountPersistence accountsDataAccess = new AccountStub();
        //        IStudentPersistence studentsDataAccess = new StudentStub
        //        (accountsDataAccess);
        //        ITutorPersistence tutorsDataAccess = new TutorStub
        //        (accountsDataAccess);
        //        credentialHandler = new CredentialHandler(accountsDataAccess);
        IAccountPersistence accountsDataAccess = Server.getAccountDataAccess();
        IStudentPersistence studentsDataAccess = Server.getStudentDataAccess();
        ITutorPersistence   tutorsDataAccess   = Server.getTutorDataAccess();
        credentialHandler = new CredentialHandler(accountsDataAccess);

        AccountCreator accountCreator = new AccountCreator();

        accountCreator
                .createAccount("fulopv@myumanitoba.ca", "Nagyon jo")
                .setStudentProfile("Victoria", "Education", "She/Her")
                .setTutorProfile("Ms. Philip", "Education", "She/Her")
                .buildAccount();
    }

    @Test
    public
    void testValidateGoodCredential()
    {
        assertTrue(credentialHandler.validateCredential("fulopv@myumanitoba.ca",
                                                        "Nagyon jo"));
    }

    @Test
    public
    void testValidateBadCredentialBad()
    {
        assertFalse(credentialHandler.validateCredential("fulopv@myumanitoba" +
                                                         ".ca",
                                                         "Nagyon Jo"));
        assertThrows(NoSuchElementException.class,
                     () -> credentialHandler.validateCredential(
                             "philipv@myumanitoba.ca",
                             "Nagyon jo"));
    }

    @Test
    public
    void testValidateEmptyCredential()
    {
        assertThrows(IllegalArgumentException.class,
                     () -> credentialHandler.validateCredential(
                             "fulopv@myumanitoba.ca",
                             ""));
        assertThrows(IllegalArgumentException.class,
                     () -> credentialHandler.validateCredential("",
                                                                "Nagyon jo"));
    }
}
