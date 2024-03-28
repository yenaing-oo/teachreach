package comp3350.teachreach.tests.unitTests.logic.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.account.AuthenticationHandler;
import comp3350.teachreach.logic.exceptions.InvalidCredentialException;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationHandlerTest {

    @Mock
    AccessAccounts accountAccess;

    @Mock
    AccessTutors tutorAccess;

    @Mock
    AccessStudents studentAccess;

    @InjectMocks
    private AuthenticationHandler authenticationHandler;

    @Before
    public void setUp() {
    }

    @Test
    public void testAuthenticateUser_ValidCredentials_ReturnsAccount() throws InvalidCredentialException {
        String email = "test@example.com";
        String plainPassword = "password";
        String storedPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";
        IAccount mockAccount = mock(IAccount.class);
        when(mockAccount.getAccountEmail()).thenReturn(email);
        when(mockAccount.getAccountPassword()).thenReturn(storedPassword);
        when(accountAccess.getAccounts()).thenReturn(Map.of(123, mockAccount));

        IAccount result = authenticationHandler.authenticateUser(email, plainPassword);

        assertNotNull(result);
        assertEquals(email, result.getAccountEmail());
    }

    @Test(expected = InvalidCredentialException.class)
    public void testAuthenticateUser_InvalidCredentials_ThrowsInvalidCredentialException() throws InvalidCredentialException {
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String rightStoredPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";
        IAccount mockAccount = mock(IAccount.class);
        when(mockAccount.getAccountEmail()).thenReturn(email);
        when(mockAccount.getAccountPassword()).thenReturn(rightStoredPassword);
        when(accountAccess.getAccounts()).thenReturn(Map.of(123, mockAccount));

        authenticationHandler.authenticateUser(email, wrongPassword);
    }

    @Test
    public void testAuthenticateTutor_ValidCredentials_ReturnsTutor() throws InvalidCredentialException {
        String email = "test@example.com";
        String password = "password";
        String storedPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";
        IAccount mockAccount = mock(IAccount.class);
        ITutor mockTutor = mock(ITutor.class);
        when(mockAccount.getAccountID()).thenReturn(123);
        when(mockAccount.getAccountEmail()).thenReturn(email);
        when(mockAccount.getAccountPassword()).thenReturn(storedPassword);
        when(accountAccess.getAccounts()).thenReturn(Map.of(123, mockAccount));
        when(tutorAccess.getTutorByAccountID(123)).thenReturn(mockTutor);

        ITutor result = authenticationHandler.authenticateTutor(email, password);

        assertNotNull(result);
    }

    @Test(expected = InvalidCredentialException.class)
    public void testAuthenticateTutor_InvalidCredentials_ThrowsInvalidCredentialException() throws InvalidCredentialException {
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String rightStoredPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";
        IAccount mockAccount = mock(IAccount.class);
        when(mockAccount.getAccountEmail()).thenReturn(email);
        when(mockAccount.getAccountPassword()).thenReturn(rightStoredPassword);
        when(accountAccess.getAccounts()).thenReturn(Map.of(123, mockAccount));

        authenticationHandler.authenticateTutor(email, wrongPassword);
    }

    @Test
    public void testAuthenticateStudent_ValidCredentials_ReturnsStudent() throws InvalidCredentialException {
        String email = "test@example.com";
        String password = "password";
        String storedPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";
        IAccount mockAccount = mock(IAccount.class);
        IStudent mockStudent = mock(IStudent.class);
        when(mockAccount.getAccountID()).thenReturn(123);
        when(mockAccount.getAccountEmail()).thenReturn(email);
        when(mockAccount.getAccountPassword()).thenReturn(storedPassword);
        when(accountAccess.getAccounts()).thenReturn(Map.of(123, mockAccount));
        when(studentAccess.getStudentByAccountID(123)).thenReturn(mockStudent);

        IStudent result = authenticationHandler.authenticateStudent(email, password);

        assertNotNull(result);
    }

    @Test(expected = InvalidCredentialException.class)
    public void testAuthenticateStudent_InvalidCredentials_ThrowsInvalidCredentialException() throws InvalidCredentialException {
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String rightStoredPassword = "$2a$12$SWvii3RMd8MYwqWuQuit5uUKj9uRDNNIFu3EdRDeTXkzhyhSGssfK";
        IAccount mockAccount = mock(IAccount.class);
        when(mockAccount.getAccountEmail()).thenReturn(email);
        when(mockAccount.getAccountPassword()).thenReturn(rightStoredPassword);
        when(accountAccess.getAccounts()).thenReturn(Map.of(123, mockAccount));

        authenticationHandler.authenticateStudent(email, wrongPassword);
    }
}

