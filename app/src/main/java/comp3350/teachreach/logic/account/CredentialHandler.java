package comp3350.teachreach.logic.account;

import java.util.Map;
import java.util.Optional;

import at.favre.lib.crypto.bcrypt.BCrypt;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.interfaces.ICredentialHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class CredentialHandler implements ICredentialHandler
{
    private static Map<Integer, IAccount> accounts;
    private        AccessAccounts         accessAccounts = null;

    public
    CredentialHandler()
    {
        this.accessAccounts        = new AccessAccounts();
        CredentialHandler.accounts = accessAccounts.getAccounts();
    }

    public
    CredentialHandler(IAccountPersistence accountPersistence)
    {
        accessAccounts             = new AccessAccounts(accountPersistence);
        CredentialHandler.accounts = accessAccounts.getAccounts();
    }

    public
    String processPassword(String plainPassword)
    {
        return BCrypt
                .withDefaults()
                .hashToString(12, plainPassword.toCharArray());
    }

    @Override
    public
    Optional<IAccount> validateCredential(String email, String plainPassword)
    {
        final boolean emptyEmail    = !InputValidator.isNotEmpty(email);
        final boolean emptyPassword = !InputValidator.isNotEmpty(plainPassword);
        final boolean invalidEmail  = !InputValidator.isValidEmail(email);
        if (emptyEmail) {
            throw new IllegalArgumentException("Email mustn't be empty");
        } else if (emptyPassword) {
            throw new IllegalArgumentException("Password mustn't be empty");
        } else if (invalidEmail) {
            throw new IllegalArgumentException("Invalid email address");
        }
        Optional<IAccount> maybeAccount = accounts
                .values()
                .stream()
                .filter(account -> account.getAccountEmail().equals(email))
                .findFirst();
        return maybeAccount
                .filter(account -> BCrypt
                        .verifyer()
                        .verify(plainPassword.toCharArray(),
                                account.getAccountPassword()).verified);
    }
}
