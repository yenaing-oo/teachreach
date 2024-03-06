package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class AccountStub implements IAccountPersistence
{

    ArrayList<IAccount> accounts;

    public
    AccountStub()
    {
        accounts = new ArrayList<>();

        //        this.storeTutor(new Tutor("Jackson Pankratz", "He/Him",
        //                "Computer Science", "pankratz25@myumanitoba.ca",
        //                "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI
        //                .RdOKAHYdRhgiw7Z5YxTd6.beOG", 13.5));
        //        this.storeTutor(new Tutor("Camryn Mcmillan", "She/Her",
        //        "Computer Science",
        //                "mcmill5@myumanitoba.ca",
        //                "$2a$12$LMXSy2E2SRxXOyUzT2hwuOV.
        //                .lVkQHj5sVFgrTQF4QpJWVbo9CBie", 20));
        //        this.storeTutor(new Tutor("Justin Huang", "He/Him",
        //        "Computer Science",
        //                "huang15@myumanitoba.ca", "$2a$12$r9yuopZw8rOLVK
        //                .L9FU6k.kaZu3GtrcTvc/PBNleKVcWx/YE6Hc4G", 17.5));
        //        this.storeTutor(new Tutor("Ashna Sharma", "She/Her",
        //        "Computer Science",
        //                "sharma7@myumanitoba.ca",
        //                "$2a$12$bnFp/uerz96t0CZwkRhNyuOFQTg54d9K0Pzkhdh/
        //                .8p2/ec1SFxjm", 11));
        //        this.storeTutor(new Tutor("Theo Brown", "They/Them",
        //        "Computer Science",
        //                "brown102@myumanitoba.ca",
        //                "$2a$12$i/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg
        //                .PXVGNnjF4ld46hJe", 40.5));
        //
        //        this.storeStudent(new Student("Rob Guderian", "He/Him",
        //        "Computer Science",
        //                "guder4@myumanitoba.ca", "$2a$12$i" +
        //                "/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg
        //                .PXVGNnjF4ld46hJe"));
    }

    @Override
    public
    IAccount storeAccount(IAccount newAccount)
    {
        return getAccountByEmail(newAccount.getAccountEmail()).orElseGet(() -> {
            accounts.add(newAccount);
            return newAccount;
        });
    }

    @Override
    public
    boolean updateAccount(IAccount existingAccount)
    {
        AtomicBoolean result = new AtomicBoolean(false);
        getAccountByEmail(existingAccount.getAccountEmail()).ifPresent(account -> {
            account.setAccountPassword(existingAccount.getAccountPassword());
            account.setAccountEmail(existingAccount.getAccountEmail());
            result.set(true);
        });
        return result.get();
    }

    @Override
    public
    List<IAccount> getAccounts()
    {
        return this.accounts;
    }

    private
    Optional<IAccount> getAccountByEmail(String email)
    {
        return accounts
                .stream()
                .filter(account -> account.getAccountEmail().equals(email))
                .findFirst();
    }
}