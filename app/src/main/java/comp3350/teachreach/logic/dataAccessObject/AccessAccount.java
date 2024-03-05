package comp3350.teachreach.logic.dataAccessObject;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.IAccount;

public class AccessAccount {
    private IAccountPersistence accountPersistence;
    private List<IAccount> accounts;
    private IAccount account;

    public AccessAccount() {
        accountPersistence = Server.getAccountDataAccess();
        accounts = null;
        account = null;
    }

    public AccessAccount(final IAccountPersistence accountPersistence) {
        this();
        this.accountPersistence = accountPersistence;
    }

    public List<IAccount> getAccounts() {
        accounts = accountPersistence.getAccounts();
        return Collections.unmodifiableList(accounts);
    }

    public Optional<IAccount> getAccountByEmail(String email) {
        if (accounts == null) {
            accounts = accountPersistence.getAccounts();
        }
        accounts.stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst()
                .ifPresentOrElse(a -> account = a, () -> {
                    account = null;
                    accounts = null;
                });
        return Optional.ofNullable(account);
    }

    public IAccount insertAccount(IAccount newAccount) {
        return accountPersistence.storeAccount(newAccount);
    }

    public boolean updateAccount(IAccount existingAccount) {
        return accountPersistence.updateAccount(existingAccount);
    }
}
