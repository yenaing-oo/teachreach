package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.account.AccountManager;
import comp3350.teachreach.logic.exceptions.AccountManagerException;
import comp3350.teachreach.logic.exceptions.InvalidCredentialException;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;
import comp3350.teachreach.logic.interfaces.IAuthenticationHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerTest {
    private final String oldPassword = "oldPassword";
    private final String newPassword = "newPassword";
    private final String newEmail = "newemail@example.com";
    private final String newName = "New Name";
    private final String pronouns = "He/Him";
    private final String major = "Computer Science";
    @Mock
    private AccessAccounts mockAccessAccounts;
    @Mock
    private IAuthenticationHandler mockAuthenticationHandler;
    @Mock
    private IAccount mockAccount;
    @InjectMocks
    private AccountManager accountManager;

    @Before
    public void setUp() throws InvalidCredentialException {
        when(mockAccount.getAccountEmail()).thenReturn("mockEmail@example.com");
        when(mockAccount.setAccountPassword(anyString())).thenReturn(mockAccount);
        when(mockAccount.setAccountEmail(anyString())).thenReturn(mockAccount);
        when(mockAccount.setUserName(anyString())).thenReturn(mockAccount);
        when(mockAccount.setUserPronouns(anyString())).thenReturn(mockAccount);
        when(mockAccount.setUserMajor(anyString())).thenReturn(mockAccount);
        when(mockAuthenticationHandler.authenticateUser(anyString(), anyString())).thenReturn(mockAccount);
        when(mockAccessAccounts.updateAccount(any())).thenReturn(mockAccount);
    }

    @Test
    public void testUpdatePassword() throws Exception {
        doReturn(mockAccount).when(mockAuthenticationHandler).authenticateUser(anyString(), anyString());
        accountManager.updatePassword(oldPassword, newPassword);

        verify(mockAuthenticationHandler).authenticateUser(anyString(), anyString());
    }

    @Test
    public void testUpdatePassword_InvalidCredentialException() throws Exception {
        doThrow(new InvalidCredentialException("Invalid credential")).when(mockAuthenticationHandler).authenticateUser(anyString(), anyString());

        assertThrows(InvalidCredentialException.class, () -> accountManager.updatePassword(oldPassword, newPassword));
    }

    @Test
    public void testUpdatePassword_InvalidPasswordException() {
        assertThrows(InvalidPasswordException.class, () -> accountManager.updatePassword(oldPassword, oldPassword));
    }

    @Test
    public void testUpdatePassword_AccountManagerException() {
        when(mockAccessAccounts.updateAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));

        assertThrows(AccountManagerException.class, () -> accountManager.updatePassword(oldPassword, newPassword));

        verify(mockAccessAccounts).updateAccount(any());
    }

    @Test
    public void testUpdateEmail() throws Exception {
        accountManager.updateEmail(oldPassword, newEmail);

        verify(mockAccessAccounts).updateAccount(any());
        verify(mockAuthenticationHandler).authenticateUser(anyString(), anyString());
    }

    @Test
    public void testUpdateEmail_InvalidCredentialException() throws Exception {
        doThrow(new InvalidCredentialException("Invalid credential")).when(mockAuthenticationHandler).authenticateUser(anyString(), anyString());
        assertThrows(InvalidCredentialException.class, () -> accountManager.updateEmail(oldPassword, newEmail));
    }

    @Test
    public void testUpdateEmail_InvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> accountManager.updateEmail(oldPassword, "invalidEmail"));
    }

    @Test
    public void testUpdateEmail_AccountManagerException() {
        when(mockAccessAccounts.updateAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));

        assertThrows(AccountManagerException.class, () -> accountManager.updateEmail(oldPassword, newEmail));

        verify(mockAccessAccounts).updateAccount(any());
    }

    @Test
    public void testUpdateAccountUsername() throws AccountManagerException {
        accountManager.updateAccountUsername(newName);

        verify(mockAccount).setUserName(newName);
        verify(mockAccessAccounts).updateAccount(mockAccount);
    }

    @Test
    public void testUpdateAccountUsername_Exception() {
        when(mockAccessAccounts.updateAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));

        assertThrows(AccountManagerException.class, () -> accountManager.updateAccountUsername(newName));
    }

    @Test
    public void testUpdateAccountUserPronouns() throws AccountManagerException {
        accountManager.updateAccountUserPronouns(pronouns);

        verify(mockAccount).setUserPronouns(pronouns);
        verify(mockAccessAccounts).updateAccount(mockAccount);
    }

    @Test
    public void testUpdateAccountUserPronouns_Exception() {
        when(mockAccessAccounts.updateAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));

        assertThrows(AccountManagerException.class, () -> accountManager.updateAccountUserPronouns(pronouns));
    }

    @Test
    public void testUpdateAccountUserMajor() throws AccountManagerException {
        accountManager.updateAccountUserMajor(major);

        verify(mockAccount).setUserMajor(major);
        verify(mockAccessAccounts).updateAccount(mockAccount);
    }

    @Test
    public void testUpdateAccountUserMajor_Exception() {
        when(mockAccessAccounts.updateAccount(any())).thenThrow(new RuntimeException("Some unexpected error occurred"));

        assertThrows(AccountManagerException.class, () -> accountManager.updateAccountUserMajor(major));
    }
}
