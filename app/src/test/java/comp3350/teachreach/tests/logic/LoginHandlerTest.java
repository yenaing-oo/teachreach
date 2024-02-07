package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.teachreach.data.AccountStub;
import comp3350.teachreach.logic.LoginHandler;
import comp3350.teachreach.objects.AccountType;

public class LoginHandlerTest {
    private LoginHandler loginHandler;

    @Before
    public void setUp() {
        System.out.println("Starting test for LoginHandler");
        loginHandler = new LoginHandler();
    }

    @Test
    public void testValidateTutorCredential() {
        assertTrue(loginHandler.validateCredential(AccountType.Tutor,
                "brown102@myumanitoba.ca", "veryStrongPassword"));
    }

    @Test
    public void testValidateTutorCredentialBad() {
        assertFalse(loginHandler.validateCredential(AccountType.Tutor,
                "brown102@myumanitoba.ca", "veryWeakPassword"));
    }

    @Test
    public void testValidateTutorCredentialEmpty() {
        assertFalse(loginHandler.validateCredential(AccountType.Tutor,
                "brown102@myumanitoba.ca", ""));
        assertFalse(loginHandler.validateCredential(AccountType.Tutor,
                "", "veryStrongPassword"));
    }
}
