package comp3350.teachreach.data;

import java.util.List;

import comp3350.teachreach.objects.IAccount;

public interface IAccountPersistence {

    IAccount storeAccount(IAccount newAccount);

    boolean updateAccount(IAccount existingAccount);

    List<IAccount> getAccounts();
}
