package comp3350.teachreach.logic.account;

import java.util.Optional;

import at.favre.lib.crypto.bcrypt.BCrypt;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.objects.IAccount;

public class CredentialHandler implements ICredentialHandler {

    private final IAccountPersistence accounts;

    public CredentialHandler() {
        this.accounts = Server.getAccountDataAccess();
    }

    public CredentialHandler(IAccountPersistence accounts) {
        this.accounts = accounts;
    }

    public String processPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    @Override
    public boolean validateCredential(String email, String password) {
        Optional<IAccount> maybeAccount = accounts.getAccountByEmail(email);
        boolean result = false;

        if (maybeAccount.isPresent()) {
            BCrypt.Result bResult =
                BCrypt.verifyer().verify(
                    password.toCharArray(),
                    maybeAccount.get().getPassword());
            result = bResult.verified;
        }

        return result;
    }
}
