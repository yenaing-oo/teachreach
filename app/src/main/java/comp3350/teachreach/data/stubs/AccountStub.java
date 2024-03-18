package comp3350.teachreach.data.stubs;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class AccountStub implements IAccountPersistence
{

    private static Map<Integer, IAccount> accounts  = null;
    private static int                    currentID = 1;

    public
    AccountStub()
    {
        if (accounts == null) {
            AccountStub.accounts = new HashMap<>();
        }

        this.storeAccount(new Account("guderr@myumanitoba.ca",
                                      "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                                      ".RdOKAHYdRhgiw7Z5YxTd6.beOG",
                                      "Robert Guderian",
                                      "He/Him",
                                      "Computer Science"));
        // accountID should be 1, password should be "veryStrongPassword"

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

    /**
     * @param newAccount A new account consist of email, password digest,
     *                   name, pronouns, and major
     * @return an IAccount object with a generated integer account identifier
     */
    @Override
    public
    IAccount storeAccount(IAccount newAccount)
    {
        return AccountStub.accounts.put(currentID++, newAccount);
    }

    /**
     * @param existingAccount An existing IAccount object consist of int
     *                        accountID, String email, String password digest,
     *                        String name, String pronouns, and String major
     * @return The updated account object
     * @throws NoSuchElementException if account isn't found
     */
    @Override
    public
    IAccount updateAccount(IAccount existingAccount)
    {
        if (accounts.containsKey(existingAccount.getAccountID())) {
            return accounts.put(existingAccount.getAccountID(),
                                existingAccount);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * @return Map from Integer accountID -> IAccount object
     */
    @Override
    public
    Map<Integer, IAccount> getAccounts()
    {
        return accounts;
    }
}