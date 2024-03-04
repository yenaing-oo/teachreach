package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.logic.dataAccessObject.AccessAccount;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessAccountIT {
    private AccessAccount accessAccount;
    private File tempDB;


    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IAccountPersistence persistence =
                new AccountHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessAccount = new AccessAccount(persistence);
    }

    @Test
    public void testGetAccounts() {
        final List<IAccount> accounts = accessAccount.getAccounts();
        assertEquals(1, accounts.size());
    }

    @Test
    public void testGetAccountByEmail() {
        final IAccount account = accessAccount
                .getAccountByEmail("pankratz25@myumanitoba.ca");
        assertNotNull(account);
    }

    @Test
    public void testGetAccountByEmailBad() {
        final IAccount account = accessAccount
                .getAccountByEmail("pankratz@myumanitoba.ca");
        assertNull(account);
    }

    @Test
    public void testStoreAccount() {
        final IAccount a = new Account("pankratz@myumanitoba.ca", "AI");
        accessAccount.insertAccount(a);
        assertEquals(2, accessAccount.getAccounts().size());
    }

    @Test
    public void testUpdateAccount() {
        final IAccount a = accessAccount.getAccountByEmail("pankratz25@myumanitoba.ca");
        final IAccount u = new Account(a.getEmail(),
                "$2a$12$i/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg.PXVGNnjF4ld46hJe");
        accessAccount.updateAccount(u);
        assertEquals(1, accessAccount.getAccounts().size());
        assertEquals(
                "$2a$12$i/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg.PXVGNnjF4ld46hJe",
                accessAccount.getAccountByEmail("pankratz25@myumanitoba.ca").getPassword());
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
