package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.Optional;

import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public interface IAccountPersistence {

    IAccount storeAccount(IAccount newAccount);

    void updateAccount(IAccount existingAccount);

    Optional<IAccount> getAccountByEmail(String email);
}
