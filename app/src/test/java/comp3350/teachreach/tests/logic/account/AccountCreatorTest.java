package comp3350.teachreach.tests.logic.account;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import comp3350.teachreach.data.exceptions.DuplicateEmailException;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.exceptions.AccountCreatorException;
import comp3350.teachreach.logic.exceptions.InvalidNameException;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

@RunWith(MockitoJUnitRunner.class)
public class AccountCreatorTest {

    private final String email = "test@email.com";
    private final String password = "password";
    private final String name = "Test User";
    private final String pronouns = "they/them";
    private final String major = "Computer Science";
    @Mock
    private IAccountPersistence mockAccountPersistence;
    @Mock
    private IStudentPersistence mockStudentPersistence;
    @Mock
    private ITutorPersistence mockTutorPersistence;
    @InjectMocks
    private AccountCreator accountCreator;
    @Mock
    private IAccount mockIAccount;
    @Mock
    private ITutor mockITutor;
    @Mock
    private IStudent mockIStudent;

    @Test
    public void testCreateStudentAccount() throws Exception {

        when(mockAccountPersistence.storeAccount(any(IAccount.class))).thenReturn(mockIAccount);
        when(mockStudentPersistence.storeStudent(any(IStudent.class))).thenReturn(mockIStudent);

        accountCreator.createStudentAccount(email, password, name, pronouns, major);

        verify(mockAccountPersistence).storeAccount(any());
        verify(mockStudentPersistence).storeStudent(any());
    }

    @Test
    public void testCreateTutorAccount() throws Exception {

        when(mockAccountPersistence.storeAccount(any())).thenReturn(mockIAccount);
        when(mockTutorPersistence.storeTutor(any())).thenReturn(mockITutor);

        accountCreator.createTutorAccount(email, password, name, pronouns, major);

        verify(mockAccountPersistence).storeAccount(any());
        verify(mockTutorPersistence).storeTutor(any());
    }

    @Test
    public void testCreateStudentAccount_DuplicateEmailException() throws Exception {

        when(mockAccountPersistence.storeAccount(any())).thenThrow(new DuplicateEmailException("Email already in use"));

        assertThrows(DuplicateEmailException.class, () -> accountCreator.createStudentAccount(email, password, name, pronouns, major));

        verify(mockAccountPersistence).storeAccount(any());
    }

    @Test
    public void testCreateStudentAccount_InvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> accountCreator.createStudentAccount("notAnEmail", password, name, pronouns, major));
    }

    @Test
    public void testCreateStudentAccount_InvalidPasswordException() {
        assertThrows(InvalidPasswordException.class, () -> accountCreator.createStudentAccount(email, " ", name, pronouns, major));
    }

    @Test
    public void testCreateStudentAccount_InvalidNameException() {
        assertThrows(InvalidNameException.class, () -> accountCreator.createStudentAccount(email, password, " ", pronouns, major));
    }

    @Test
    public void testCreateStudentAccount_AccountCreatorException() throws Exception {
        when(mockAccountPersistence.storeAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));
        assertThrows(AccountCreatorException.class, () -> accountCreator.createStudentAccount(email, password, name, pronouns, major));
    }

    @Test
    public void testCreateTutorAccount_DuplicateEmailException() throws Exception {
        when(mockAccountPersistence.storeAccount(any())).thenThrow(new DuplicateEmailException("Email already in use"));

        assertThrows(DuplicateEmailException.class, () -> accountCreator.createTutorAccount(email, password, name, pronouns, major));

        verify(mockAccountPersistence).storeAccount(any());
    }

    @Test
    public void testCreateTutorAccount_InvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> accountCreator.createTutorAccount("notAnEmail", password, name, pronouns, major));
    }

    @Test
    public void testCreateTutorAccount_InvalidPasswordException() {
        assertThrows(InvalidPasswordException.class, () -> accountCreator.createTutorAccount(email, " ", name, pronouns, major));
    }

    @Test
    public void testCreateTutorAccount_InvalidNameException() {
        assertThrows(InvalidNameException.class, () -> accountCreator.createTutorAccount(email, password, " ", pronouns, major));
    }

    @Test
    public void testCreateTutorAccount_AccountCreatorException() throws Exception {
        when(mockAccountPersistence.storeAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));

        assertThrows(AccountCreatorException.class, () -> accountCreator.createTutorAccount(email, password, name, pronouns, major));
    }

}
