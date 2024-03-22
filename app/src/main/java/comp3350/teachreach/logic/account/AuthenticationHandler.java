package comp3350.teachreach.logic.account;

import java.util.Map;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.exceptions.DataAccessException;
import comp3350.teachreach.logic.exceptions.InvalidCredentialException;
import comp3350.teachreach.logic.interfaces.IAuthenticationHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class AuthenticationHandler implements IAuthenticationHandler {
    private static Map<Integer, IAccount> accounts;
    private AccessAccounts accessAccounts;
    private AccessTutors tutorAccess;
    private AccessStudents studentAccess;

    public AuthenticationHandler() {
        this.accessAccounts = new AccessAccounts();
        this.tutorAccess = new AccessTutors();
        this.studentAccess = new AccessStudents();

        AuthenticationHandler.accounts = accessAccounts.getAccounts();
    }

    public AuthenticationHandler(IAccountPersistence accountPersistence) {
        accessAccounts = new AccessAccounts(accountPersistence);
        AuthenticationHandler.accounts = accessAccounts.getAccounts();
    }

    @Override
    public IAccount authenticateUser(String email, String plainPassword) throws InvalidCredentialException {
        for (IAccount account : accounts.values()) {
            if (account.getAccountEmail().equals(email)) {
                boolean passwordMatches = PasswordManager.verifyPassword(plainPassword, account.getAccountPassword());
                if (passwordMatches) {
                    return account;
                }
            }
        }
        throw new InvalidCredentialException("Invalid username or password");
    }

    @Override
    public ITutor authenticateTutor(String email, String plainPassword) throws InvalidCredentialException {
        try {
            IAccount account = authenticateUser(email, plainPassword);
            return tutorAccess.getTutorByAccountID(account.getAccountID());
        } catch (DataAccessException e) {
            throw new InvalidCredentialException("Invalid username or password");
        }
    }

    @Override
    public IStudent authenticateStudent(String email, String plainPassword) throws InvalidCredentialException {
        try {
            IAccount account = authenticateUser(email, plainPassword);
            return studentAccess.getStudentByAccountID(account.getAccountID());
        } catch (DataAccessException e) {
            throw new InvalidCredentialException("Invalid username or password");
        }
    }


}
