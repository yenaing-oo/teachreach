package comp3350.teachreach.logic.account;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import at.favre.lib.crypto.bcrypt.BCrypt;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.interfaces.ICredentialHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class CredentialHandler implements ICredentialHandler
{
    private static List<IAccount> accounts;
    private final  AccessAccounts accessAccounts;

    public
    CredentialHandler()
    {
        this.accessAccounts        = new AccessAccounts();
        CredentialHandler.accounts = accessAccounts.getAccounts();
    }

    public
    CredentialHandler(IAccountPersistence accounts)
    {
        this();
        CredentialHandler.accounts = accounts.getAccounts();
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
    boolean validateCredential(String email, String password)
    {
        final boolean emptyEmail    = !InputValidator.isNotEmpty(email);
        final boolean emptyPassword = !InputValidator.isNotEmpty(password);
        final boolean invalidEmail  = !InputValidator.isValidEmail(email);
        if (emptyEmail) {
            throw new IllegalArgumentException("Email mustn't be empty");
        } else if (emptyPassword) {
            throw new IllegalArgumentException("Password mustn't be empty");
        } else if (invalidEmail) {
            throw new IllegalArgumentException("Invalid email address");
        }
        Optional<IAccount> maybeAccount = accounts
                .stream()
                .filter(account -> account.getAccountEmail().equals(email))
                .findFirst();
        return maybeAccount
                .map(account -> BCrypt
                        .verifyer()
                        .verify(password.toCharArray(),
                                account.getAccountPassword()).verified)
                .orElseThrow(() -> new NoSuchElementException(
                        "No account found with the provided email."));
    }
}
