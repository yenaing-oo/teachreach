package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public interface IAccountPersistence {

    Account storeAccount(Account newAccount);

    Account getAccountByEmail(String email);
}
