package comp3350.teachreach.tests.logic;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.exceptions.DuplicateEmailException;
import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.exceptions.AccountCreatorException;
import comp3350.teachreach.logic.exceptions.InvalidNameException;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;
import comp3350.teachreach.logic.interfaces.IAccountCreator;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccountCreatorTest {
    private File tempDB;
    private IAccountCreator accountCreator;
    private String validStudentEmail;
    private String validPassword;
    private String validName;
    private String validMajor;
    private String validPronouns;
    private String validTutorEmail;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting a new test for AccountCreator");
        this.tempDB = TestUtils.copyDB();
        String tempDBPath = this.tempDB.getAbsolutePath().replace(".script", "");
        IAccountPersistence accountsDataAccess = new AccountHSQLDB(tempDBPath);
        IStudentPersistence studentsDataAccess = new StudentHSQLDB(tempDBPath);
        ITutorPersistence tutorsDataAccess = new TutorHSQLDB(tempDBPath);

        this.validStudentEmail = "johndoe@email.com";
        this.validTutorEmail = "johndoe2@email.com";
        this.validPassword = "password";
        this.validName = "John Doe";
        this.validMajor = "Computer Science";
        this.validPronouns = "he/him";

        accountCreator = new AccountCreator(
                accountsDataAccess,
                studentsDataAccess,
                tutorsDataAccess);
    }

    @Test
    public void testCreateTutorValid() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createTutorAccount(validTutorEmail, validPassword, validName, validPronouns, validMajor);
    }

    @Test
    public void testCreateStudentValid() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createStudentAccount(validStudentEmail, validPassword, validName, validPronouns, validMajor);
    }

    @Test(expected = InvalidNameException.class)
    public void testCreateStudentAccountInvalidName() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createStudentAccount(validStudentEmail, validPassword, "  ", validPronouns, validMajor);
    }

    @Test(expected = InvalidEmailException.class)
    public void testCreateStudentInvalidEmail() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createStudentAccount("user123", validPassword, validName, validPronouns, validMajor);
    }

    @Test(expected = DuplicateEmailException.class)
    public void testCreateStudentDuplicateEmail() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createStudentAccount(validStudentEmail, validPassword, validName, validPronouns, validMajor);
        accountCreator.createStudentAccount(validStudentEmail, validPassword, validName, validPronouns, validMajor);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testCreateStudentInvalidPassword() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createStudentAccount(validStudentEmail, "  ", validName, validPronouns, validMajor);
    }

    @Test(expected = InvalidNameException.class)
    public void testCreateTutorAccountInvalidName() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createTutorAccount(validTutorEmail, validPassword, "  ", validPronouns, validMajor);
    }

    @Test(expected = InvalidEmailException.class)
    public void testCreateTutorInvalidEmail() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createTutorAccount("user123", validPassword, validName, validPronouns, validMajor);
    }

    @Test(expected = DuplicateEmailException.class)
    public void testCreateTutorDuplicateEmail() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createTutorAccount(validTutorEmail, validPassword, validName, validPronouns, validMajor);
        accountCreator.createTutorAccount(validTutorEmail, validPassword, validName, validPronouns, validMajor);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testCreateTutorInvalidPassword() throws InvalidNameException, DuplicateEmailException, InvalidPasswordException, InvalidEmailException, AccountCreatorException {
        accountCreator.createTutorAccount(validTutorEmail, "  ", validName, validPronouns, validMajor);
    }
}
