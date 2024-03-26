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
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
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
    private AccessAccounts accessAccounts;
    @Mock
    private AccessStudents accessStudents;
    @Mock
    private AccessTutors accessTutors;
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

        when(accessAccounts.insertAccount(any(IAccount.class))).thenReturn(mockIAccount);
        when(accessStudents.insertStudent(any(IStudent.class))).thenReturn(mockIStudent);

        accountCreator.createStudentAccount(email, password, name, pronouns, major);

        verify(accessAccounts).insertAccount(any());
        verify(accessStudents).insertStudent(any());
    }

    @Test
    public void testCreateTutorAccount() throws Exception {

        when(accessAccounts.insertAccount(any())).thenReturn(mockIAccount);
        when(accessTutors.insertTutor(any())).thenReturn(mockITutor);

        accountCreator.createTutorAccount(email, password, name, pronouns, major);

        verify(accessAccounts).insertAccount(any());
        verify(accessTutors).insertTutor(any());
    }

    @Test
    public void testCreateStudentAccount_DuplicateEmailException() throws Exception {

        when(accessAccounts.insertAccount(any())).thenThrow(new DuplicateEmailException("Email already in use"));

        assertThrows(DuplicateEmailException.class, () -> accountCreator.createStudentAccount(email, password, name, pronouns, major));

        verify(accessAccounts).insertAccount(any());
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
        when(accessAccounts.insertAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));
        assertThrows(AccountCreatorException.class, () -> accountCreator.createStudentAccount(email, password, name, pronouns, major));
    }

    @Test
    public void testCreateTutorAccount_DuplicateEmailException() throws Exception {
        when(accessAccounts.insertAccount(any())).thenThrow(new DuplicateEmailException("Email already in use"));

        assertThrows(DuplicateEmailException.class, () -> accountCreator.createTutorAccount(email, password, name, pronouns, major));

        verify(accessAccounts).insertAccount(any());
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
        when(accessAccounts.insertAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));

        assertThrows(AccountCreatorException.class, () -> accountCreator.createTutorAccount(email, password, name, pronouns, major));
    }

}
