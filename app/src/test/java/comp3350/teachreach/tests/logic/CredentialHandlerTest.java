package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.teachreach.logic.account.CredentialHandler;
import comp3350.teachreach.objects.AccountType;

public class CredentialHandlerTest {
    private CredentialHandler credentialHandler;

    @Before
    public void setUp() {
        System.out.println("Starting test for LoginHandler");
        credentialHandler = new CredentialHandler();
    }

    @Test
    public void testValidateTutorCredential() {
        assertTrue(credentialHandler.validateCredential(AccountType.Tutor,
                "brown102@myumanitoba.ca", "veryStrongPassword"));
    }

    @Test
    public void testValidateTutorCredentialBad() {
        assertFalse(credentialHandler.validateCredential(AccountType.Tutor,
                "brown102@myumanitoba.ca", "veryWeakPassword"));
    }

    @Test
    public void testValidateTutorCredentialEmpty() {
        assertFalse(credentialHandler.validateCredential(AccountType.Tutor,
                "brown102@myumanitoba.ca", ""));
        assertFalse(credentialHandler.validateCredential(AccountType.Tutor,
                "", "veryStrongPassword"));
    }
}
