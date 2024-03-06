package comp3350.teachreach.logic.DAOs;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class AccessAccounts
{
    private static IAccountPersistence accountPersistence;
    private static List<IAccount>      accounts;
    private        IAccount            account;

    public
    AccessAccounts()
    {
        accountPersistence = Server.getAccountDataAccess();
        accounts           = null;
        account            = null;
    }

    public
    AccessAccounts(final IAccountPersistence accountPersistence)
    {
        this();
        AccessAccounts.accountPersistence = accountPersistence;
    }

    public
    List<IAccount> getAccounts()
    {
        if (accounts == null) {
            AccessAccounts.accounts = accountPersistence.getAccounts();
        }
        return Collections.unmodifiableList(accounts);
    }

    public
    Optional<IAccount> getAccountByEmail(String email)
    {
        if (accounts == null) {
            AccessAccounts.accounts = accountPersistence.getAccounts();
        }
        accounts
                .parallelStream()
                .filter(a -> a.getAccountEmail().equals(email))
                .findFirst()
                .ifPresentOrElse(a -> account = a, () -> {
                    account  = null;
                    accounts = null;
                });
        return Optional.ofNullable(account);
    }

    public
    IAccount insertAccount(IAccount newAccount)
    {
        return accountPersistence.storeAccount(newAccount);
    }

    public
    boolean updateAccount(IAccount existingAccount)
    {
        return accountPersistence.updateAccount(existingAccount);
    }
}
