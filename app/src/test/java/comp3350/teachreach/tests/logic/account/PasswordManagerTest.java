package comp3350.teachreach.tests.logic.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comp3350.teachreach.logic.account.PasswordManager;

public class PasswordManagerTest {

    @Test
    public void testEncryptPassword_ValidPassword_ReturnsHashedPassword() {
        String plainPassword = "password";
        String hashedPassword = PasswordManager.encryptPassword(plainPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(plainPassword, hashedPassword);
    }

    @Test
    public void testVerifyPassword_CorrectPassword_ReturnsTrue() {
        String plainPassword = "password";
        String correctHashedPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";

        boolean result = PasswordManager.verifyPassword(plainPassword, correctHashedPassword);

        assertTrue(result);
    }

    @Test
    public void testVerifyPassword_IncorrectPassword_ReturnsFalse() {
        String correctHashedPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";
        String incorrectPassword = "wrongpassword";

        boolean result = PasswordManager.verifyPassword(incorrectPassword, correctHashedPassword);

        assertFalse(result);
    }
}
