package comp3350.teachreach.data;

import java.util.List;
import java.util.Optional;

import comp3350.teachreach.objects.IAccount;

public interface IAccountPersistence {

    IAccount storeAccount(IAccount newAccount);

    void updateAccount(IAccount existingAccount);

    Optional<IAccount> getAccountByEmail(String email);

    List<IAccount> getAccounts();
}
