package comp3350.teachreach.logic.DAOs;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class AccessAccount
{
    private static IAccountPersistence accountPersistence;
    private static List<IAccount>      accounts;
    private        IAccount            account;

    public
    AccessAccount()
    {
        accountPersistence = Server.getAccountDataAccess();
        accounts           = null;
        account            = null;
    }

    public
    AccessAccount(final IAccountPersistence accountPersistence)
    {
        this();
        AccessAccount.accountPersistence = accountPersistence;
    }

    public synchronized
    List<IAccount> getAccounts()
    {
        if (accounts == null) {
            AccessAccount.accounts = accountPersistence.getAccounts();
        }
        return Collections.unmodifiableList(accounts);
    }

    //CHANGE FROM EMAIL TO ID LATER
    public
    Optional<IAccount> getAccountByEmail(String email)
    {
        if (accounts == null) {
            AccessAccount.accounts = accountPersistence.getAccounts();
        }
        accounts
                .stream()
                .filter(a -> a.getEmail().equals(email))
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
