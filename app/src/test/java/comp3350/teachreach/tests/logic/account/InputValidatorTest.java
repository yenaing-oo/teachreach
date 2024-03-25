package comp3350.teachreach.tests.logic.account;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import org.junit.Test;

import comp3350.teachreach.logic.account.InputValidator;
import comp3350.teachreach.logic.exceptions.InvalidNameException;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidInputException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;

public class InputValidatorTest {

    @Test
    public void testValidateEmail_ValidEmail_NoExceptionThrown() {
        String email = "test@example.com";

        try {
            InputValidator.validateEmail(email);
        } catch (Exception e) {
            fail("No exception should be thrown for a valid email.");
        }
    }

    @Test
    public void testValidateEmail_EmptyEmail_ThrowsInvalidEmailException() {
        String email = "";

        assertThrows(InvalidEmailException.class, () -> InputValidator.validateEmail(email));
    }

    @Test
    public void testValidateEmail_InvalidEmail_ThrowsInvalidEmailException() {
        String email = "invalid_email";

        assertThrows(InvalidEmailException.class, () -> InputValidator.validateEmail(email));
    }

    @Test
    public void testValidatePassword_ValidPassword_NoExceptionThrown() {
        String password = "testpassword";

        try {
            InputValidator.validatePassword(password);
        } catch (Exception e) {
            fail("No exception should be thrown for a valid password.");
        }
    }

    @Test
    public void testValidatePassword_EmptyPassword_ThrowsInvalidPasswordException() {
        String password = "";

        assertThrows(InvalidPasswordException.class, () -> InputValidator.validatePassword(password));
    }

    @Test
    public void testValidateName_ValidName_NoExceptionThrown() {
        String name = "John Doe";

        try {
            InputValidator.validateName(name);
        } catch (Exception e) {
            fail("No exception should be thrown for a valid name.");
        }
    }

    @Test
    public void testValidateName_EmptyName_ThrowsInvalidNameException() {
        String name = "";

        assertThrows(InvalidNameException.class, () -> InputValidator.validateName(name));
    }

    @Test
    public void testValidateInput_ValidInput_NoExceptionThrown() {
        String input = "valid input";

        try {
            InputValidator.validateInput(input);
        } catch (Exception e) {
            fail("No exception should be thrown for a valid input.");
        }
    }

    @Test
    public void testValidateInput_EmptyInput_ThrowsInvalidInputException() {
        String input = "";

        assertThrows(InvalidInputException.class, () -> InputValidator.validateInput(input));
    }
}

